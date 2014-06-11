import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by server-pc on 11/06/14.
 */
public class DNSServer3 {

    private HashMap<String,String> HotDomaine=null;
    private int PortDNS=56;
    public DNSServer3()
    {
        HotDomaine=new HashMap<String, String>();
        HotDomaine.put("www.site1.com","192.168.40.1");
        HotDomaine.put("www.site2.cn","192.168.40.2");
        HotDomaine.put("www.site3.de","192.168.40.3");
        HotDomaine.put("www.site4.ma","192.168.40.4");
        HotDomaine.put("www.site5.org","192.168.40.5");
        HotDomaine.put("www.site6.fr","192.168.40.6");

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
        DNSServer3 dnsserver=new DNSServer3();
        DatagramSocket server=new DatagramSocket(dnsserver.PortDNS);

        while(true)
        {
            System.out.println("DNS connected...");
            byte[] sendbyte=new byte[1024];
            byte[] receivebyte=new byte[1024];
            DatagramPacket receiver=new DatagramPacket(receivebyte,receivebyte.length);
            server.receive(receiver);
            String str=new String(receiver.getData());
            String s=str.trim();
            System.out.println("Request from client :"+s);
            InetAddress addr=receiver.getAddress();
            int port=receiver.getPort();

            String ip=dnsserver.ResolutDirect(s);
            String name=dnsserver.ResolutInverse(s);

            if(name!=null)
            {

                sendbyte=("Reponse DNS 3:"+name).getBytes();
                DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                server.send(sender);
                //  break;
            }
            if(ip!=null)
            {

                sendbyte=("Reponse DNS 3:"+ip).getBytes();
                DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                server.send(sender);
                // break;
            }
            if(ip==null && name==null)
            {
                System.out.println("No Response...");
                sendbyte=("null").getBytes();
                DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                server.send(sender);
            }

        }

    }
}
