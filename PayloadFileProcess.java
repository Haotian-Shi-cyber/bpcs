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
            allBytes.add(chunk);//each chunk is one byte data
        
        bitify();// convert each char into 8 bits
        blockify();// group bytes into blocks
    }

    public byte[] buildByteFile(String fileName) throws IOException, FileNotFoundException {
        File payload = new File("Payloads/" + fileName);
        int fileLength = (int) payload.length();

        //System.out.println("File size: " + fileLength + " B");
        //System.out.println("File name: " + fileName);
        
        FileInputStream fis = new FileInputStream(payload);
        

        byte[] byteFile = new byte[fileLength + fileName.length() + 5];
        int contentStart = modifyHeader(fileLength, fileName, byteFile);
        fis.read(byteFile, contentStart, (int) payload.length());
        //System.out.println(payload.length());
        return byteFile;
    }

    public int modifyHeader(int fileLength, String fileName, byte[] byteForm){
        //System.out.println(fileName.length());
        //System.out.println(byteForm.length);

        for (int k = 0; k < 4; k++){
            byteForm[k] = (byte) ((fileLength >> (24 - k*8)) & 0xFF);// read the length of fileload
        }
        byteForm[4] = (byte) (fileName.length());
        System.out.println(byteForm[4]);
        System.out.println(byteForm[4]);

        int j = 0;
        for(int i = 5; i < 5 + fileName.length(); i++){
            byteForm[i] = (byte)(fileName.charAt(j++));
            //System.out.println(byteForm[i]);//print filename
        }
        return fileName.length() + 5;// return start position
    }

    public void bitify(){
        int q = 0;
        bitForm = new int[allBytes.size() * 8];
        for(byte val : allBytes)
            for(int k = 0; k < 8;k++)
                bitForm[q++] = (val >> (7 - k)) % 2;
    //    for (int i = 0; i < 32; i++)
      //      System.out.println(bitForm[i]);
    }

    public void blockify(){
        /*
         * divide bits flow into separate blocks, each block has 64 bits
         */
        int p = 0;
        blocks = new Block[(int) Math.ceil(bitForm.length / 63.0)];
        for (int j = 0; j < bitForm.length; j+=63) {
           int[] feed = new int[64];
           for (int k = 1; (j + k - 1) < bitForm.length && k < 64; k++)
               feed[k] = bitForm[j + k - 1];
           blocks[p] = new Block(feed);
           p++;
        }
        System.out.println(p);
    }

    public int blockLength() {
        /*
         * return blocks' length
         */
        return blocks.length;
    }

    public int getNumOfConjugated() {
        int count = 0;
        for(Block block:blocks)
            if(block.isConjugated())
                count++;
        return count;

    }

    public Block getBlock(int i) {
        return blocks[i];
    }

}
