import java.util.*;

public class Hider {
    public static void main(String[] args) throws Exception{
        Scanner key = new Scanner(System.in);

        /* create ImageRead obj header that cause pbc to cgc conversion and create bit planes */
        System.out.print("Enter name of vessel image: ");
        ImageReader vessel = new ImageReader("vessel/" + key.next());
        System.out.println("Vessel processed. ");
        
        /* get all hider segments */
        List<Segment> vesHideSegs = vessel.getHiderSegs();

        /* get noise region's size avaliable to hide data*/
        System.out.println("Noise region avaliable(converted to bytes):" + 
                vesHideSegs.size() * 64 / 8192 + " kilobytes");// reason to mutiply 64 is because each seg is 8*8 size seg
        
        /* deal with hider file */
        System.out.println("Enter name of files you want to hide: ");
        PayloadFileProcess payload = new PayloadFileProcess(key.next());
        
        /* print header file information */
        System.out.println("Payload processed.");
        System.out.println("Total # of blocks: " + payload.blockLength());
        System.out.println("converted to bytes: " + payload.blockLength() * 64 / 8192 + " KB");
        System.out.println(payload.getNumOfConjugated() + " blocks needed to be conjugated.");
        
        /* make sure paylaod is no bigger than limitations */
        if(payload.blockLength() > vesHideSegs.size())
            throw new Exception("Payload too big!");
        
        /* replace noise region block with payload blocks */
        for(int j = 0; j < payload.blockLength(); j++)
            vesHideSegs.get(j).replaceWith(payload.getBlock(j));
        System.out.println("Data now is hidden"); 
        
        /* generate result image */
        System.out.println("Enter name of result image: ");
        ResultImage result = new ResultImage(key.next());
        result.processPlanes(vessel.getRGBPlanes(), vessel.getAlphaPlanes());
        result.constructImage();

        System.out.println("Result image generated!");

        key.close();

    }
}
