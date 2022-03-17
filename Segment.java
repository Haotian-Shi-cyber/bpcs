import java.util.*;

public class Segment{
    private int row, col, layer;
    private int[][] pl;
    
    /* segment attribute */
    public Segment(int r, int c, int[][] plane, int i) {
        row = r;
        col = c;
        pl = plane;
        layer = i;
    }
    
    /* get segment's number of changes */
    public int getBorder(){
        int changes = 0;

        //number of changes by same row
        for (int r = row; r < row + 8;r++)// 8 loops
            for (int c = col+1; c < col + 8;c++) // 7 loops
                if(pl[r][c] != pl[r][c-1])
                    changes++;

        //number of changes by same col
        for (int c = col; c < col + 8;c++)
            for(int r = row+1; r < row + 8; r++)
                if(pl[r][c] != pl[r-1][c])
                    changes++;
        //System.out.println(changes + "\n");       
        return changes;
    }
    
    /* get noise region */
    public boolean isNoise(){
        return (getBorder() / 64.0) > 0.3;
    }
    
    /* replace segment with payload block */
    public void replaceWith(Block data) {
        int[][] temp = data.getBlock();

        for(int r = row; r < row + 8; r++)
            for(int c = col; c < col + 8; c++)
                pl[r][c] = temp[r - row][c - col];
    }
    
    /* segment planes */
    public int[][] getSegMatrix() {
        int[][] temp = new int[8][8];

        for(int r = row; r < row + 8; r++)
            for(int c = col; c < col + 8; c++)
                temp[r - row][c - col] = pl[r][c];

        return temp;
    }
}
