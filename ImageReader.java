import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;


public class ImageReader {
    private Pixel[][] pixels;
    private int width, height;
    private Plane[] rgbPlanes, alphaPlanes;
    public ImageReader(String fileName) throws IOException {
        BufferedImage image = ImageIO.read(new File(fileName));

        width = image.getWidth();
        height = image.getHeight();

        pixels = new Pixel[height][width];

        System.out.println("Height: " + height + " Width: " + width);

        for(int j = 0; j < height; j++) 
        	for(int k = 0; k < width; k++) 
        		pixels[j][k] = new Pixel(image.getRGB(k, j));

        bitPlane();
    }

    public ImageReader() throws IOException {
        this("Vessel.png");
    }

    public void bitPlane() {
        rgbPlanes = new Plane[24];
        alphaPlanes = new Plane[8];

        for(int i = 0; i < 24; i++) { //24 rgb bit planes
            int[][] rgb = new int[height][width];
            for(int j = 0; j < height; j++) 
                for(int k = 0; k < width; k++) 
                    rgb[j][k] = pixels[j][k].getCGCBit(i);
            rgbPlanes[i] = new Plane(rgb, i);
        }

        for(int i = 0; i < 8; i++) { // 8 alpha planes
            int[][] alpha = new int[height][width];
            for(int j = 0; j < height; j++) 
                for(int k = 0; k < width; k++) 
                    alpha[j][k] = pixels[j][k].getAlphaBit(i);
            alphaPlanes[i] = new Plane(alpha, i);
        }


    }

    public List<Segment> getHiderSegs(){
        List<Segment> hiderSegments = new ArrayList<Segment>();
        for (int i = 0; i < 24; i++) 
            hiderSegments.addAll(rgbPlanes[i].getNoiseSegofPlane());//get all RGB Noise-like region
        for (int i = 0; i < 1; i++)
            hiderSegments.addAll(alphaPlanes[i].getAllSeg());

        return hiderSegments;
    }

    public Plane[] getRGBPlanes(){
        return rgbPlanes;
    }

    public Plane[] getAlphaPlanes(){
        return alphaPlanes;
    }

}
