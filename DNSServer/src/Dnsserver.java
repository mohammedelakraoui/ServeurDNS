import java.io.IOException;
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
    private int[]  PortDNS={53,54,55};

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
            DatagramSocket server=new DatagramSocket(53);
            Dnsserver dnsserver=new Dnsserver();
            while(true)
            {
                System.out.println("DNS connecté...");
                byte[] sendbyte=new byte[1024];
                byte[] receivebyte=new byte[1024];
                DatagramPacket receiver=new DatagramPacket(receivebyte,receivebyte.length);
                server.receive(receiver);
                String str=new String(receiver.getData());
                String req=str.trim();
                System.out.println("Tram du client est : "+req);
                InetAddress addr=receiver.getAddress();
                int port=receiver.getPort();

                String ip=dnsserver.ResolutDirect(req);
                String name=dnsserver.ResolutInverse(req);

                    if(name!=null)
                    {
                        System.out.println("Envoie Name");
                        sendbyte=("DNS Autorité: "+name).getBytes();
                        DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                        server.send(sender);
                      //  break;
                    }
                    if(ip!=null)
                    {
                        System.out.println("Envoie IP");
                        sendbyte=("DNS Autorité: "+ip).getBytes();
                        DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                        server.send(sender);
                      // break;
                    }
                if(name==null && ip==null){
                        System.out.println("Distribution d'order");
                        sendbyte=dnsserver.Distribuer(req).getBytes();
                        DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                        server.send(sender);
                    }

            }
       /* }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }*/
    }


   private  String Distribuer(String requete) throws IOException {
       String Data="";
       for (int i=0;i<this.PortDNS.length;i++)
       {
        DatagramSocket client=new DatagramSocket();
        InetAddress addr=InetAddress.getByName("127.0.0.1");
        byte[] sendbyte=new byte[1024];
        byte[] receivebyte=new byte[1024];
        sendbyte=requete.getBytes();
        DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,54);
        client.send(sender);
        DatagramPacket receiver=new DatagramPacket(receivebyte,receivebyte.length);
        client.receive(receiver);
        String s=new String(receiver.getData());
        Data=s.trim();
        //System.out.println("IP address ou nom de domaine: "+s.trim());
        //System.out.println("---------------------");
        client.close();
       }
       return Data;

    }










}



