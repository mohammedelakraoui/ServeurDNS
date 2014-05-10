import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by server-pc on 23/04/14.
 */
public class DNSserver1 {

    private HashMap<String,String> HotDomaine=null;


    public DNSserver1()
    {

        HotDomaine=new HashMap<String, String>();
        HotDomaine.put("www.google.com","192.77.40.1");
        HotDomaine.put("www.moi.cn","192.168.999.2");
        HotDomaine.put("www.oui.de","192.168.784.3");
        HotDomaine.put("www.non.ma","192.168.78.4");
        HotDomaine.put("www.images.org","192.168.30.5");
        HotDomaine.put("www.site.fr","192.168.40.45");
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
        DatagramSocket server=new DatagramSocket(54);
        DNSserver1 dnsserver=new DNSserver1();
        while(true)
        {
            System.out.println("DNS connecté...");
            byte[] sendbyte=new byte[1024];
            byte[] receivebyte=new byte[1024];
            DatagramPacket receiver=new DatagramPacket(receivebyte,receivebyte.length);
            server.receive(receiver);
            String str=new String(receiver.getData());
            String s=str.trim();

            InetAddress addr=receiver.getAddress();
            int port=receiver.getPort();

            String ip=dnsserver.ResolutDirect(s);
            String name=dnsserver.ResolutInverse(s);

            if(name!=null)
            {
                System.out.println(" Envoie Name");
                sendbyte=("Reponse du DNS1:"+name).getBytes();
                DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                server.send(sender);
                //  break;
            }
            if(ip!=null)
            {
                System.out.println("Envoie IP");
                sendbyte=("Reponse du DNS1:"+ip).getBytes();
                DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                server.send(sender);
                // break;
            }


        }

     }
}
