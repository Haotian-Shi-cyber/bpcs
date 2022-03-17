import java.util.*;

public class Plane{
    private int layer, row, col;
    private List<Segment> segments;
    private int[][] plane;
    
    /* segment plane into segments */
    public Plane(int[][] pl, int i){
        //pl represents 2d cgc plane, i is numth of plane.
        plane = pl.clone();
        layer = i;

        segments = new ArrayList<Segment>();
        row = plane.length;
        col = plane[0].length;

        for(int r = 0; r < row; r+=8)
            for(int c = 0; c < col; c+=8)
                segments.add(new Segment(r, c, plane, layer));   
    }
    
    /* return whole segments */
    public List<Segment> getAllSeg() {
        return segments;
    }
    
    /* return noise region segments */
    public List<Segment> getNoiseSegofPlane() {
        List<Segment> complexSeg = new ArrayList<Segment>();
        for(Segment segment:segments)
            if (segment.isNoise())
                complexSeg.add(segment);
        return complexSeg;
    }
    
    /* get width of planes */
    public int getWidth() {
        return col;
    }
    
    /* get height of planes */
    public int getHeight() {
        return row;
    }
    
    /* get specific bit */
    public int getBit(int r, int c) {
        return plane[r][c];
    }
}
