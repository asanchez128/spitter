package edu.harding.comp431;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 
 * @author fmccown
 */
public class ImageUtilities {     
    

    /**
     * Write the given input stream to the filename specified.
     * @param inputStream
     * @param filename 
     */
    public static void writeImage(InputStream inputStream, String filename) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filename);
            int ch = inputStream.read();
            while (ch != -1) {
                    os.write(ch);
                    ch = inputStream.read();
            }
            os.close(); 
            
        } catch (Exception ex) {
            Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(ActionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Resize a given image to the specified width and height. Set height
     * to zero to keep the same aspect ratio.
     * 
     * Code modified from example obtained here:
     * http://greatwebguy.com/programming/java/java-image-resizer-servlet/
     * 
     * @param imageFilename
     * @param formatType
     * @param width
     * @param height 
     */
    public static void resizeImage(String imageFilename, String formatType, 
            int width, int height) {
        try {
            // Read the original image 
            BufferedImage bufferedImage = ImageIO.read(new File(imageFilename));
            
            // Calculate new height (if not specified) keeping the aspect ratio 
            int calcHeight = height > 0 ? height : (width * 
                    bufferedImage.getHeight() / bufferedImage.getWidth());
            
            // Write the image
            FileOutputStream os = new FileOutputStream(imageFilename);
            ImageIO.write(createResizedCopy(bufferedImage, width, calcHeight), 
                    formatType, os);
            
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, 
                    "Problem resizing image " + imageFilename, ex);
        }
    }
    
    private static BufferedImage createResizedCopy(Image originalImage, 
            int scaledWidth, int scaledHeight) {
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, 
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledBI.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;    
    }
    
    public static boolean imageExists(String filename) {
        File f = new File(filename);
        return f.exists();
    }
}
