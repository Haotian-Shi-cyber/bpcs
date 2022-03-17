import java.util.*;
import java.io.*;

public class PayloadFileProcess {
    private List<Byte> allBytes;
    private int[] bitForm;
    private Block[] blocks;

    /* constructor to process payload file */
    public PayloadFileProcess(String fileName) throws IOException, FileNotFoundException {
        allBytes = new ArrayList<Byte>();
        
        System.out.println("file is hidden");
        byte[] temp = buildByteFile(fileName);
        for(byte chunk:temp)
            allBytes.add(chunk);//each chunk is one byte data
        
        bitify();// convert each char into 8 bits
        blockify();// group bytes into blocks
    }
    
    /* create payload bytes flow */
    public byte[] buildByteFile(String fileName) throws IOException, FileNotFoundException {
        File payload = new File("Payloads/" + fileName);
        int fileLength = (int) payload.length();

        System.out.println("File size: " + (fileLength / 1024) + " KB");                                                    
        System.out.println("File name: " + fileName);   

        FileInputStream fis = new FileInputStream(payload);
        

        byte[] byteFile = new byte[fileLength + fileName.length() + 5];
        int contentStart = modifyHeader(fileLength, fileName, byteFile);
        fis.read(byteFile, contentStart, (int) payload.length());

        return byteFile;
    }
    
    /* add header bits into bitflow */
    public int modifyHeader(int fileLength, String fileName, byte[] byteForm){

        for (int k = 0; k < 4; k++){
            byteForm[k] = (byte) (((fileLength >> (24 - k*8)) & 0xFF)-128);// read the length of fileload
        }
        byteForm[4] = (byte) (fileName.length()-128);

        int j = 0;
        for(int i = 5; i < 5 + fileName.length(); i++){
            byteForm[i] = (byte)(fileName.charAt(j++)-128);
            //System.out.println(byteForm[i]);//print filename
        }
        return fileName.length() + 5;// return start position
    }
    
    /* convert bytes flow into bits flow */
    public void bitify(){
        int q = 0;
        bitForm = new int[allBytes.size() * 8];
        for(byte val : allBytes)
            for(int k = 0; k < 8;k++)
                bitForm[q++] = ((val + 128)>> (7 - k)) % 2;
    }
    
    
    /* divide bits flow into separate blocks, each block has 64 bits */
    public void blockify(){
        int p = 0;
        blocks = new Block[(int) Math.ceil(bitForm.length / 63.0)];
        for (int j = 0; j < bitForm.length; j+=63) {
           int[] feed = new int[64];
           for (int k = 1; (j + k - 1) < bitForm.length && k < 64; k++)
               feed[k] = bitForm[j + k - 1];
           blocks[p] = new Block(feed);
           p++;
        }
    }

    /* return blocks' length */
    public int blockLength() {
        return blocks.length;
    }

    /* get number of conjugated blocks */
    public int getNumOfConjugated() {
        int count = 0;
        for(Block block:blocks)
            if(block.isConjugated())
                count++;
        return count;

    }
    
    /* get block by index */
    public Block getBlock(int i) {
        return blocks[i];
    }

}
