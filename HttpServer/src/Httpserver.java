import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by server-pc on 11/06/14.
 */
public class Httpserver {

    /**
     * WebServer constructor.
     */

    public  String sendAndGetDataFromDNS(String data)
    {
        String datarecieve=null;
        try
        {
            // boolean switch_=true;
            //  while(true)
            //   {
            DatagramSocket client=new DatagramSocket();
            InetAddress addr=InetAddress.getByName("127.0.0.1");

            byte[] sendbyte=new byte[1024];
            byte[] receivebyte=new byte[1024];
            BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
            //  System.out.println("Entrer le Nom du Domaine ou IP adress:");
            //String str=in.readLine();
            sendbyte=data.getBytes();
            DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,50);
            client.send(sender);
            DatagramPacket receiver=new DatagramPacket(receivebyte,receivebyte.length);
            client.receive(receiver);
            datarecieve=new String(receiver.getData());
            System.out.println("IP address ou nom de domaine: " + datarecieve.trim());
            System.out.println("---------------------");
            client.close();

            return datarecieve.trim();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return datarecieve;
    }

    protected void start() {
        ServerSocket s;

        System.out.println("HTTP server starting up on port 8080");
        System.out.println("(press ctrl-c to exit)");
        try {
            // create the main server socket
            s = new ServerSocket(8080);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return;
        }

        System.out.println("Waiting for connection");
        for (;;) {
            try {
                // wait for a connection
                Socket remote = s.accept();
                // remote is now the connected socket
                System.out.println("Connection, sending data.");
                BufferedReader in = new BufferedReader(new InputStreamReader(remote.getInputStream()));
                PrintWriter out = new PrintWriter(remote.getOutputStream());
                String GetData=in.readLine().split("//")[1].split("HTTP")[0];
                System.out.println(GetData);
                String response=sendAndGetDataFromDNS(GetData);

                /*String str = ".";
                while (!str.equals("")) {
                    str = in.readLine();
                }*/

                // Send the response
                // Send the headers
                out.println("HTTP/1.0 200 OK");
                out.println("Content-Type: text/html");
                out.println("Server: DNS");
                // this blank line signals the end of the headers
                out.println("");
                // Send the HTML page
                out.println("<H1>Response: "+response+"</H2>");
                out.flush();
                remote.close();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }


    public static void main(String args[]) {
        Httpserver ws = new Httpserver();
        ws.start();
    }
}
