import java.util.*;

public class Segment{
    private int row, col, layer;
    private int[][] pl;
    
    public Segment(int r, int c, int[][] plane, int i) {
        row = r;
        col = c;
        pl = plane;
        layer = i;
    }

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

    public boolean isNoise(){
        return (getBorder() / 64.0) > 0.3;
    }
    
    public void replaceWith(Block data) {
        int[][] temp = data.getBlock();
        for(int r = row; r < row + 8; r++)
            for(int c = col; c < col + 8; c++)
                pl[r][c] = temp[r][c];
    }
}
