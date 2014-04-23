import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by server-pc on 04/04/14.
 */
public class Dnsserver  {

    private HashMap<String,String> HotDomaine=null;


    public Dnsserver()
    {

        HotDomaine=new HashMap<String, String>();
        HotDomaine.put("www.site1.com","192.168.40.1");
        HotDomaine.put("www.site2.cn","192.168.40.2");
        HotDomaine.put("www.site3.de","192.168.40.3");
        HotDomaine.put("www.site4.ma","192.168.40.4");
        HotDomaine.put("www.site5.org","192.168.40.5");
        HotDomaine.put("www.site6.fr","192.168.40.6");
        //------------------

    }


    public String  ResolutDirect(String NomDemaine)
    {
        String AdressIP="";
        AdressIP=(String) HotDomaine.get(NomDemaine);
        return AdressIP;

    }

    public String  ResolutInverse(String IP)
    {

        String NomDomaine="";

        for(Map.Entry<String,String> entry : HotDomaine.entrySet())
        {
            if(entry.getValue().toUpperCase().equals(IP.toUpperCase()))
            {
            NomDomaine=entry.getKey();
            }

        }

        return NomDomaine;

    }

//---------------------------------------------------


  /*  public static void main (String args[]) {
/** default port */
      /*  Dnsserver dnsserver=new Dnsserver();
        int port = 1234;
        ServerSocket server = null;
        Socket currentConnexion;
        InputStream socketInputStream;
        OutputStream socketOuputStream;
        try {
            server = new ServerSocket(port);
        }
        catch (IOException ex)
        {
            // For any reason, impossible to create the socket on the required port.
            System.err.println("Impossible de créer un socket serveur sur ce port : "+ex);

            try { // trying an anonymous one.
                server = new ServerSocket(0);
            }
            catch (IOException ex2) { // Impossible to connect!
                System.err.println("Impossible de créer un socket serveur : "+ex2);
            }
        }

        if (null != server ) {
            try {
                System.out.println("DNS en attente de connexion sur le port : "+server.getLocalPort());
                while (true) {

                    currentConnexion = server.accept();
                    //;
                    System.out.print("Connecting.....");
                    InetAddress localAdr = InetAddress.getLocalHost();

                    OutputStream out = currentConnexion.getOutputStream();
                    //out.write(dnsserver.ResolutDirect("www.site1.com").getBytes());

                    out.write(localAdr.getByName("").toString().getBytes());
                    out.flush();
                    currentConnexion.close();
                    System.out.println("Done!");
                }
            }
            catch (Exception ex) {
                // Error of connection
                System.err.println("Une erreur est survenue : "+ex);
                ex.printStackTrace();
            }
        }



    }*/

    public static void main(String args[])
    {
        try
        {
            DatagramSocket server=new DatagramSocket(1309);
            Dnsserver dnsserver=new Dnsserver();
            while(true)
            {
                byte[] sendbyte=new byte[1024];
                byte[] receivebyte=new byte[1024];
                DatagramPacket receiver=new DatagramPacket(receivebyte,receivebyte.length);
                server.receive(receiver);
                String str=new String(receiver.getData());
                String s=str.trim();
                System.out.println("Tram du client est : "+s);
                InetAddress addr=receiver.getAddress();
                int port=receiver.getPort();
                String ip=dnsserver.ResolutDirect(s);
                String name=dnsserver.ResolutInverse(s);
                System.out.println(name);
                    if(ip.isEmpty())
                    {
                        sendbyte=name.getBytes();
                        DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                        server.send(sender);
                        break;
                    }
                    else if(name.isEmpty())
                    {
                        sendbyte=ip.getBytes();
                        DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                        server.send(sender);
                        break;
                    }
                else{
                        sendbyte="HTTP Error IP ou Host n'exist pas".getBytes();
                        DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                        server.send(sender);
                    }

            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }










}



