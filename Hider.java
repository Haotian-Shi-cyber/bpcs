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
                vesHideSegs.size() * 63 / 8192 + " kilobytes");// reason to mutiply 63 is because each seg is 8*8 size seg

        System.out.println("Enter name of files you want to hide (separated by semicolons): ");

    }
}
