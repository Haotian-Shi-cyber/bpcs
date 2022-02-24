import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ResultImage{
    private int height, width;
    private String name;
    private Pixel[][] pixels;

    public ResultImage(String name) {
        this.name = "StegResults/" + name;
    }

    public void processPlanes(Plane[] rgbPlanes, Plane[] alphaPlanes) {
        height = rgbPlanes[0].getHeight();
        width = rgbPlanes[0].getWidth();
        pixels = new Pixel[height][width];

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++) {

                int[] rgb = new int[24];
                for(int k = 0; k < 24; k++) 
                    rgb[k] = rgbPlanes[k].getBit(i, j);

                int[] alpha = new int[8];
                for(int k = 0; k < 8; k++)
                    alpha[k] = alphaPlanes[k].getBit(i,j);

                pixels[i][j] = new Pixel(rgb,alpha);
                
            }
        }
            
    }
    
    public void constructImage() throws IOException { 
        BufferedImage img = new BufferedImage(width, height, 2);//2 is TYPE_INT_ARGB

        /* constructing new image containing hided file */
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                img.setRGB(j,i, pixels[i][j].getRGB());

        System.out.println(this.name);       
        ImageIO.write(img, "png", new File(name));
    }
}


