import com.mrolorin.*;
import com.mrolorin.RollshopTester;
import com.mrolorin.bruteforcer.Bruteforcer;

public class App {

    // TODO: Logger
    public static void main(String[] args) {
        RollshopTester rollshopTester = new RollshopTester(new HttpUrlConnectionClient());
        Bruteforcer bruteforce = new Bruteforcer(rollshopTester, new NumberGenerator());
        try {
            String password = bruteforce.hack(65);
            System.out.println("The password is \"" + password + "\"");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
