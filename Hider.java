import java.util.*;

public class Hider {
    public static void main(String[] args) throws Exception{
        Scanner key = new Scanner(System.in);

        /* create ImageRead obj header that cause pbc to cgc conversion and create bit planes */
        System.out.print("Enter name of vessel image: ");
        ImageReader vessel = new ImageReader("vessel/" + key.next());
        System.out.print("Vessel processed. ");
    }
}
