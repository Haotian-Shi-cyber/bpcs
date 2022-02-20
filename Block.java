public class Block {
    private int[][] bl;
    private boolean conjugated;

    public Block(int[] block){
        bl = new int[8][8];
        int q = 0;
        for(int r = 0; r < 8; r++)
            for(int c = 0; c < 8; c++){
                bl[r][c] = block[q];
                q++;
            }
        if(!isComplex())
          conjugate(); // conjugate it if no complex enough 
    }
    
    public int getBorder() {
        int changes = 0;

        //number of changes by same row
        for (int r = 0; r < 8;r++)// 8 loops
            for (int c = 1; c < 8;c++) // 7 loops
                if(bl[r][c] != bl[r][c-1])
                    changes++;

        //number of changes by same col
        for (int c = 0; c < 8;c++)
            for(int r = 1; r < 8; r++)
                if(bl[r][c] != bl[r-1][c])
                    changes++;

        return changes;
        
    }

    public boolean isComplex() {
        return (getBorder() / 64.0) > 0.3; 
    }

    public void conjugate() {
        for(int r = 0; r < 8; r++)
            for(int c = 0; c < 8; c++)
                /*
                 * checkererboard pattern please refer to papar written by Eiji Kawaguchi and Richard O.Eason
                 * "Principle and applications of BPCS-Steganography"
                 */
                bl[r][c] ^= ((r % 2 == 0)?((c % 2 == 0)? 0 : 1) : ((c % 2 == 0)? 1 : 0)); // XOR  between payload block and checkerboard pattern
        bl[0][0] = 1;
        conjugated = true;
    }

    public boolean isConjugated() {
        return conjugated;
    }

    public int[][] getBlock(){
        return bl;
    }
}
