import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class ImageReader {
    private Pixel[][] pixels;
    private int width, height;

    public ImageReader(String fileName) throws IOException {
        BufferedImage image = ImageIO.read(new File(fileName));

        width = image.getWidth();
        height = image.getHeight();

        pixels = new Pixel[height][width];

        System.out.println("Height: " + height + " Width: " + width);

        for(int j = 0; j < height; j++) 
        	for(int k = 0; k < width; k++) 
        		pixels[j][k] = new Pixel(image.getRGB(k, j));
    }

    public ImageReader() throws IOException {
        this("Vessel.png");
    }

}
