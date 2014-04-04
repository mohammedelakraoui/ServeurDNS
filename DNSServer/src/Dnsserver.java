import java.net.ServerSocket;

/**
 * Created by server-pc on 04/04/14.
 */
public class Dnsserver {

    private ServerSocket server=null;
    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }


}
