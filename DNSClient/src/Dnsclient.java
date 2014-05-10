import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by server-pc on 04/04/14.
 */
public class Dnsclient {

  /*  public static void main(String args[]) throws Exception
    {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress defaultDnsServer = InetAddress.getByName("localhost" );
        byte[] query = new byte[1024];
        byte[] response = new byte[2048];
        query[0] = 0x00;
        query[1] = 0x01;
        query[2] = 0x00;
        query[3] = 0x00;
        query[4] = 0x00;
        query[5] = 0x01;
        query[6] = 0x00;
        query[7] = 0x00;
        query[8] = 0x00;
        query[9] = 0x00;
        query[10] = 0x00;
        query[11] = 0x00;
        query[12] = 0x02;
        query[13] = 0x62;
        query[14] = 0x6a;
        query[15] = 0x03;
        query[16] = 0x63;
        query[17] = 0x6f;
        query[18] = 0x6c;
        query[19] = 0x03;
        query[20] = 0x63;
        query[21] = 0x6f;
        query[22] = 0x6d;
        query[23] = 0x02;
        query[24] = 0x63;
        query[25] = 0x6e;
        query[26] = 0x00;
        query[27] = 0x00;
        query[y;
        query[29] = 0x00;
        query[30] = 0x01;
        DatagramPacket queryPacket = new DatagramPacket(query, query.length, defaultDnsServer, 53);
        //clientSocket.send(queryPacket);
        DatagramPacket responsePacket = new DatagramPacket(response, response.length);
        //clientSocket.receive(responsePacket);
        String question = new String(queryPacket.getData());
        //String result = new String(responsePacket.getData());
        System.out.println("Data sent :" + question);
        //System.out.println("Data received :" + result);
        clientSocket.close();
    }*/

    public static void main(String args[])
    {
        try
        {
            boolean switch_=true;
            while(switch_)
            {
            DatagramSocket client=new DatagramSocket();
            InetAddress addr=InetAddress.getByName("127.0.0.1");

            byte[] sendbyte=new byte[1024];
            byte[] receivebyte=new byte[1024];
            BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Entrer le Nom du Domaine ou IP adress:");
            String str=in.readLine();
            sendbyte=str.getBytes();
            DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,53);
            client.send(sender);
            DatagramPacket receiver=new DatagramPacket(receivebyte,receivebyte.length);
            client.receive(receiver);
            String s=new String(receiver.getData());
            System.out.println("IP address ou nom de domaine: "+s.trim());
            System.out.println("---------------------");
            client.close();
                System.out.println("Vous voullez appler le DNS encore une fois? y/n");
                if(System.in.read()=='n' || System.in.read()=='N')
                {switch_=false;}
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

}




