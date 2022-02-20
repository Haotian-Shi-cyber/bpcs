import java.util.*;

public class Plane{
    private int layer, row, col;
    private List<Segment> segments;
    private int[][] plane;

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
    
    public List<Segment> getAllSeg() {
        return segments;
    }

    public List<Segment> getNoiseSegofPlane() {
        List<Segment> complexSeg = new ArrayList<Segment>();
        for(Segment segment:segments)
            if (segment.isNoise())
                complexSeg.add(segment);
        return complexSeg;
    }
}
