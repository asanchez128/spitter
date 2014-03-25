package edu.harding.comp431;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * This is the main servlet that performs all the work on behalf of the JSPs.
 * 
 * @author fmccown
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"}, initParams = {
    @WebInitParam(name = "jdbc_conn", 
    value = "jdbc:mysql://taz.harding.edu/asanchez?user=asanchez&password=H01535297"),
    @WebInitParam(name = "thumb_size", value="50")})
@MultipartConfig(fileSizeThreshold=1024*1024, 
        maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class ActionServlet extends HttpServlet {
 
    private Database spitterDatabase;
    private int thumbnailWidth;
    
    private String jdbcConnectionString;
    
    @Override
    public void init() throws ServletException {
        
        try {
            // Load JDBC driver for MySql
            Class.forName("com.mysql.jdbc.Driver").newInstance();   
            
            ServletConfig sc = getServletConfig();
                        
            // Make database available to entire application
            // 
            jdbcConnectionString = sc.getInitParameter("jdbc_conn");
            spitterDatabase = new Database(jdbcConnectionString);
            ServletContext servletContext = getServletConfig().getServletContext();
            servletContext.setAttribute("spitterDatabase", spitterDatabase);
            
            thumbnailWidth = Integer.parseInt(sc.getInitParameter("thumb_size"));
            System.out.println("thumbnailWidth = " + thumbnailWidth);                       
        } 
        catch (Exception ex) {
            throw new ServletException("Initialization Error", ex);
        }
        
        super.init();
    }
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //This will be a really long function
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = null;       
                
        // If form data is encrypted with multipart/form-data then need to
        // access fields a little differently
        String contentType = request.getContentType();
        if (contentType != null && 
            contentType.toLowerCase().indexOf("multipart/form-data") > -1 ) {
            action = getParamValue(request, "action");
        }
        else {
            action = request.getParameter("action");
        }
        
        System.out.println("ActionServlet: action = " + action);
        
        if (action == null) {            
            response.sendRedirect("login.jsp");
        }
        else if (action.equalsIgnoreCase("login")) {
            response.sendRedirect("login.jsp");
        }
        else if (action.equalsIgnoreCase("validate-login")) {
            validateLogin(request, response);
        }
        else if (action.equalsIgnoreCase("logout")) {
            // TODO: Log out the user by removing the "login" variable from HttpSession
        }
        else if (action.equalsIgnoreCase("create-account")) {
            createAccount(request, response);
        }
        else if (action.equalsIgnoreCase("edit-account")) {
            editAccount(request, response);
        }
        else if (action.equalsIgnoreCase("view")) {
            response.sendRedirect("index.jsp");
        }        
        else {
            System.out.println("Unrecognized action = " + action);
            response.sendRedirect("login.jsp");
        }
    }
   
    /**
     * This function ensures that the username/password pair are stored in
     * the database. 
     * 
     * TODO: Fix this function since it currently does no password validation.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void validateLogin(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String username = request.getParameter("username");
        SpitterUser user = spitterDatabase.getUser(username.trim());
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("login", "true");        

            // See if profile pic exists
            setProfilePic(user);            
        }
        
        // Don't show any error messages
        session.removeAttribute("error");
        
        response.sendRedirect("index.jsp");
    }
    
    /**
     * Set the user's profile pic since this information is not saved in
     * the database. This function tests to see if the user has a usernamge.jpg
     * file, and if not, uses the default.jpg.
     * @param user 
     */
    void setProfilePic(SpitterUser user) {
        String imageFile = user.getUsername() + ".jpg"; 
        String fullPath = this.getServletContext().getRealPath(
                            "images\\" + imageFile);
        if (ImageUtilities.imageExists(fullPath))
            user.setProfilePic(imageFile);    
        else
            System.out.println(fullPath + " does NOT exist");
    }
    
    /**
     * Return back the MD5 hash of a given string.
     * Function modified from example found at
     * http://stackoverflow.com/questions/415953/generate-md5-hash-in-java
     * 
     * @param message
     * @return 
     */
    private String MD5(String message) {
        try {            
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytesOfMessage = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytesOfMessage.length; i++) {
                sb.append(Integer.toHexString((bytesOfMessage[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        
     private void createAccount(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
        HttpSession session = request.getSession();
        String errorMessage = null;      
        
        String username = getParamValue(request, "username");
        String password = getParamValue(request, "password");
        String about = getParamValue(request, "about");
        
        // Get content type which is used later for making sure JPG was given
        Part part = request.getPart("image");
        String contentType = part.getContentType();
        System.out.println("Content Type = " + contentType);              
        
        // Varify all required fields were submitted
        if (username == null || username.trim().length() == 0) {
            errorMessage = "Please supply a username.";
        }
        else if (!isValidUsername(username)) {
            errorMessage = "Please supply a username that is composed of only " +
                    "letters, numbers, and underscores.";
        }
        else if (password == null || password.length() == 0) {
            errorMessage = "Please supply a password.";
        }
        else if (part.getSize() > 0 && 
                !contentType.toLowerCase().startsWith("image/jpeg")) {
            errorMessage = "Sorry, but only JPG images are acceptable.";
        }
        else {           
            String passwordHash = MD5(password);
            
            SpitterUser user = new SpitterUser(username, passwordHash, about);
            
            if (spitterDatabase.insertUser(user)) {
                session.setAttribute("user", user);
                session.setAttribute("login", "true");
                
                // Save a thumbnail image if it was supplied
                if (part.getSize() > 0) {      
                    saveThumbnail(part, user.getUsername());                                   
                }
                
                setProfilePic(user);         
                
                session.setAttribute("user", user);
            }
            else {
                errorMessage = "Sorry, but that username is taken. " +
                        "Please try another.";
            }
        }
        
        // Send them on their way if nothing went wrong
        if (errorMessage == null) {
            session.setAttribute("error", "Your account was created. Please login.");
            response.sendRedirect("login.jsp");
        }
        else {
            session.setAttribute("error", errorMessage);
            response.sendRedirect("create-account.jsp");
        }
    }
     
    private void editAccount(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
        HttpSession session = request.getSession();
        String errorMessage = null;             
        
        String password = getParamValue(request, "password");
        String about = getParamValue(request, "about");
        
        if (password == null)
            password = "";
        
        if (about == null)
            about = "";
        
        // Get content type which is used later for making sure JPG was given
        Part part = request.getPart("image");
        String contentType = part.getContentType();
        System.out.println("Content Type = " + contentType);              
        
        // Varify all required fields were submitted
        if (part.getSize() > 0 && 
                !contentType.toLowerCase().startsWith("image/jpeg")) {
            errorMessage = "Sorry, but only JPG images are acceptable.";
        }
        else {
            // Change the submitted properties of the active user
            SpitterUser user = (SpitterUser)session.getAttribute("user");
            
            if (password.length() > 0) {
                user.setPassword(MD5(password));
            }
            
            about = about.trim();
            user.setAbout(about);

            // Save changes to the database
            if (spitterDatabase.updateUser(user)) {
                
                // Save a thumbnail image if it was supplied
                if (part.getSize() > 0) {      
                    saveThumbnail(part, user.getUsername());                                   
                }
                
                setProfilePic(user);                
            }
            else {
                errorMessage = "Sorry, there was some problem updating the database.";
            }
        }
        
        // Send them on their way if nothing went wrong
        if (errorMessage == null) {
            session.removeAttribute("error");
            response.sendRedirect("index.jsp");
        }
        else {
            session.setAttribute("error", errorMessage);
            response.sendRedirect("edit-account.jsp");
        }
    }
    
    private void saveThumbnail(Part part, String username) {
        try {
            String imageFilename = username + ".jpg";

            // Writing file to NetBeansProjects\\Spitter\\build\\web\\images\\username.jpg
            String outputfile = this.getServletContext().getRealPath(
                    "images\\" + imageFilename);
            System.out.println("outputfile = " + outputfile);

            // Write the uploaded image's bytes to file
            ImageUtilities.writeImage(part.getInputStream(), outputfile);

            // Now resize the saved image
            ImageUtilities.resizeImage(outputfile, "jpg", thumbnailWidth, 
                    thumbnailWidth);
        } catch (IOException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Return true if the username is composed of only alphanumeric characters
     * and underscores.
     * @param username
     * @return 
     */
    private boolean isValidUsername(String username) {
        username = username.toUpperCase();
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            if ((c < '0' || (c > '9' && c < 'A') || c > 'Z') && c != '_') {
                return false;
            }
        }
        return true;
    }
    
    
    private String getParamValue(HttpServletRequest request, String fieldName) 
            throws ServletException {
        
        String value = null;
        try {            
            Part p = request.getPart(fieldName);
            if (p == null) {
                Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE,  
                        "getFormFieldValue: " + fieldName + " not found in form");
            }
            else {
                Scanner s = new Scanner(p.getInputStream());
                value = s.nextLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchElementException ex) {
            // Form value exists but has no content
            value = "";
        }
        
        return value;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
