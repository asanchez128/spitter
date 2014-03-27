package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import edu.harding.comp431.Database;
import edu.harding.comp431.ImageUtilities;
import java.util.ArrayList;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/heading.jspf");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      edu.harding.comp431.SpitterUser user = null;
      synchronized (session) {
        user = (edu.harding.comp431.SpitterUser) _jspx_page_context.getAttribute("user", PageContext.SESSION_SCOPE);
        if (user == null){
          user = new edu.harding.comp431.SpitterUser();
          _jspx_page_context.setAttribute("user", user, PageContext.SESSION_SCOPE);
        }
      }
      out.write('\n');
      out.write('\n');
    
    // TODO: Make sure the user is logged in by checking "login" in session.
	// If not logged in, redirect to login.jsp
    
    
    // Get database from the servlet context
    Database spitterDatabase = (Database)application.getAttribute("spitterDatabase");
    if (spitterDatabase == null) {
        response.sendRedirect("ActionServlet?action=view");
        return;
    }
    

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Spitter</title>\n");
      out.write("        \n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\" />\n");
      out.write("        \n");
      out.write("        <script type=\"text/javascript\" src=\"http://cs.harding.edu/fmccown/jquery.js\"></script>\n");
      out.write("        <script type=\"text/javascript\" src=\"main.js\"></script>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        ");
      out.write("\n");
      out.write("\n");
      out.write("<div class=\"section heading\">\n");
      out.write("    \n");
      out.write("    <script src=\"search.js\"></script>\n");
      out.write("    \n");
      out.write("    <a class=\"header\" href=\"index.jsp\">Spitter</a>\n");
      out.write("    <div class=\"search\">\n");
      out.write("        <form method=\"get\" action=\"search.jsp\">\n");
      out.write("            <input type=\"text\" id=\"search\" name=\"query\" placeholder=\"Search\" />                           \n");
      out.write("        </form>  \n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("    <a href=\"ActionServlet?action=logout\" class=\"logout\">Logout</a>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("        \n");
      out.write("        // Two ways to pull out information from database\n");
      out.write("        <div class=\"section profile\">\n");
      out.write("            <img src=\"");
      out.print( "images/" + user.getProfilePic() );
      out.write("\"\n");
      out.write("                 class=\"profile-image\" width=\"50\" height=\"50\" alt=\"Profile Pic\"/>\n");
      out.write("            <span class=\"profile-username\">");
      out.print( user.getUsername() );
      out.write("</span>\n");
      out.write("            <br />\n");
      out.write("            <span class=\"profile-about\">\n");
      out.write("                ");
      out.write(org.apache.jasper.runtime.JspRuntimeLibrary.toString((((edu.harding.comp431.SpitterUser)_jspx_page_context.findAttribute("user")).getAbout())));
      out.write("\n");
      out.write("            </span>\n");
      out.write("            <a class=\"edit-profile-link\" href=\"edit-account.jsp\">Edit</a>\n");
      out.write("            \n");
      out.write("            <table>\n");
      out.write("                <tr>\n");
      out.write("                    <td width=\"70\">\n");
      out.write("                        <span id=\"speets-total\" class=\"profile-stats-num\">\n");
      out.write("                           ???\n");
      out.write("                        </span><br />\n");
      out.write("                        <a href=\"speets.jsp?limit\" id=\"speets-link\" class=\"profile-stats-label\">Speets</a>\n");
      out.write("                    </td>\n");
      out.write("                    <td width=\"70\">\n");
      out.write("                        <span class=\"profile-stats-num\">\n");
      out.write("                            ");
      out.print( spitterDatabase.getFollowingTotal(user.getUsername()) );
      out.write("\n");
      out.write("                        </span><br />\n");
      out.write("                        <a href=\"following.jsp\" id=\"following-link\" class=\"profile-stats-label\">Following</a>\n");
      out.write("                    </td>\n");
      out.write("                    <td width=\"70\">\n");
      out.write("                        <span class=\"profile-stats-num\">\n");
      out.write("                            ???\n");
      out.write("                        </span><br />\n");
      out.write("                        <a href=\"followers.jsp\" id=\"followers-link\" class=\"profile-stats-label\">Followers</a>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("            </table>\n");
      out.write("            <br />\n");
      out.write("            <form method=\"post\" action=\"ActionServlet?action=new-speet\">\n");
      out.write("                <textarea name=\"speet\" rows=\"5\" cols=\"28\" maxlength=\"145\"\n");
      out.write("                          id=\"speet-text\"></textarea>\n");
      out.write("                <br />\n");
      out.write("                <span class=\"chars-remaining\">145</span> &nbsp; &nbsp;\n");
      out.write("                <input type=\"submit\" id=\"submit-speet\" value=\"Speet\" />\n");
      out.write("            </form>\n");
      out.write("        </div>\n");
      out.write("        \n");
      out.write("        <div class=\"section speets\" id=\"main\">\n");
      out.write("            Loading...\n");
      out.write("        </div>        \n");
      out.write("        \n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
