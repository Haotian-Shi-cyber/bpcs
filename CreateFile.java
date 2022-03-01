import java.util.*;
import java.io.*;

public class CreateFile {
    private List<Character> text;
    private int[] bitForm;

    public CreateFile(List<Segment> segments) {
        bitForm = new int[segments.size() * 64];
        int q = 0;

        for (Segment seg:segments) {
            int[][] temp = seg.getSegMatrix();
            
            if (temp[0][0] == 1){
            for(int r = 0; r < 8 ;r++)
                for(int c = 0; c < 8; c++ )
                    temp[r][c] ^= ((r % 2 == 0)? ((c % 2 == 0)? 0:1):((c%2==0)?1:0));
            }
            for(int i = 0; i < 8;i++)
                for(int j = (i==0)? 1:0; j < 8; j++)
                    bitForm[q++] = temp[i][j];
        }
    }

    public void contructFile() throws IOException {
        int fileLength = getLength();
        String filename = getFileName();

        int prefixLen = 40 + filename.length() * 8;
        
        byte[] byteForm = new byte[fileLength];

        for(int m = prefixLen; m < prefixLen + fileLength * 8;m += 8) {
            int store = 0;
            for(int n = 0; n < 8; n++)
                store += bitForm[m+n] << (7-n);
            byteForm[(m - prefixLen) / 8] = (byte) (store);
        }
        
        
        FileOutputStream fos = new FileOutputStream("Extracted/" + filename);
        fos.write(byteForm);
        fos.close();

    }
    
    public int getLength() {
        int filelength = 0;
        for(int m = 0; m < 32; m++)
            filelength += bitForm[m] << (31 - m);
        
        System.out.println(filelength);
        return filelength;
    }

    public String getFileName() {
        int lengthOfname = 0;
        for(int m = 32; m < 40; m++) {
            lengthOfname += bitForm[m] << (39 - m);
        }

        String filename = "";

        for(int m = 40; m < 40 + lengthOfname * 8;m+=8) {
            char store = 0;
            for(int n = 0; n < 8; n++)
                store += bitForm[m+n] << (7 - n);
            filename += store;
        }
        return filename;
    }
}
