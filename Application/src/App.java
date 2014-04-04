import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by server-pc on 04/04/14.
 */
public class App {
    public static void main(String[] args) throws UnknownHostException {
        for(InetAddress addr : InetAddress.getAllByName("www.stackoverflow.com"))
            System.out.println(addr.getHostAddress());
    }
}
