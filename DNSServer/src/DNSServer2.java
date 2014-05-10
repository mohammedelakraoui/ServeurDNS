import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by server-pc on 23/04/14.
 */
public class DNSServer2 {

    private HashMap<String,String> HotDomaine=null;


    public DNSServer2()
    {

        HotDomaine=new HashMap<String, String>();
        HotDomaine.put("www.apres.com","192.168.745.996");
        HotDomaine.put("www.vous.cn","192.168.47.287");
        HotDomaine.put("www.nous.de","192.168.32.03");
        HotDomaine.put("www.site-leclair.ma","192.168.784.4");
        HotDomaine.put("www.carreffour.org","192.845.40.5");
        HotDomaine.put("www.esgi.fr","192.168.447.6");
        //------------------

    }


    public String  ResolutDirect(String NomDemaine)
    {
        String AdressIP=null;
        AdressIP=(String) HotDomaine.get(NomDemaine);
        return AdressIP;

    }

    public String  ResolutInverse(String IP)
    {

        String NomDomaine=null;

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


    public static void main(String args[]) throws IOException {
        //  try
        // {
        DatagramSocket server=new DatagramSocket(55);
        DNSServer2 dnsserver=new DNSServer2();
        while(true)
        {
            System.out.println("DNS connecté...");
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

            if(name!=null)
            {
                System.out.println("Envoie Name");
                sendbyte=("Reponse du DNS2: "+name).getBytes();
                DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                server.send(sender);
                //  break;
            }
            if(ip!=null)
            {
                System.out.println("Envoie IP");
                sendbyte=("Reponse du DNS2: "+ip).getBytes();
                DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                server.send(sender);
                // break;
            }


        }

}
}
