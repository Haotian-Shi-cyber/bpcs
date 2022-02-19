import java.util.*;
import java.io.*;

public class PayloadFileProcess {
    private List<Byte> allBytes;
    private int[] bitForm;
    private Block[] blocks;

    public PayloadFileProcess(String fileName) throws IOException, FileNotFoundException {
        allBytes = new ArrayList<Byte>();
        
        System.out.println("file is hidden");
        byte[] temp = buildByteFile(fileName);
        for(byte chunk:temp)
            allBytes.add(chunk);
        
        bitify();// convert each char into 8 bits
        blockify();// group bytes into blocks
    }

    public byte[] buildByteFile(String fileName) throws IOException, FileNotFoundException {
        File payload = new File("Payloads/" + fileName);
        int fileLength = (int) payload.length();

        System.out.println("File size: " + (fileLength / 1024) + " KB");
        System.out.println("File name: " + fileName);
        
        FileInputStream fis = new FileInputStream(payload);
        

        byte[] byteFile = new byte[fileLength + fileName.length()];
        int contentStart = modifyHeader(fileLength, fileName, byteFile);
        fis.read(byteFile, contentStart, (int) payload.length());

        return byteFile;
    }

    public int modifyHeader(int fileLength, String fileName, byte[] byteForm){
    
        return fileName.length();// return start position
    }

    public void bitify(){
        int q = 0;
        bitForm = new int[allBytes.size() * 8];
        for(byte val : allBytes)
          for(int k = 0; k < 8;k++)
              bitForm[q++] = (val >> (7 - k)) % 2;

    }

    public void blockify(){
        int p = 0;
        blocks = new Block[(int) Math.ceil(bitForm.length / 63.0)];
        for (int j = 0; j < bitForm.length; j+=63) {
           int[] feed = new int[64];
           for (int k = 1; (j + k - 1) < bitForm.length && k < 64; k++)
               feed[k] = bitForm[j + k - 1];
           blocks[p++] = new Block(feed);
        }
    }


}
