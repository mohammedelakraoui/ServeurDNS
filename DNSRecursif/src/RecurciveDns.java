import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


  public class RecurciveDns  {


        private int[]  PortDNS={54,55,56};
        private int NextDns=0;

        public static void main(String args[]) throws IOException {
            //  try
            // {

            DatagramSocket server=new DatagramSocket(50);
            RecurciveDns dnsserver=new RecurciveDns();

            while(true)
            {
                System.out.println("DNS Recursive connected...");
                byte[] sendbyte=new byte[1024];
                byte[] receivebyte=new byte[1024];
                DatagramPacket receiver=new DatagramPacket(receivebyte,receivebyte.length);
                server.receive(receiver);
                String str=new String(receiver.getData());
                String req=str.trim();
                System.out.println("Request from client : "+req);
                InetAddress addr=receiver.getAddress();
                int port=receiver.getPort();
                System.out.println("Waiting response from dns server ...");
                sendbyte=dnsserver.distribuer(req).getBytes();
                // Reponse to http server
                System.out.println("Send data to client !!!");
                DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                server.send(sender);
               //client.close();

            }

        }


        private  String distribuer(String requete) throws IOException {
                 String Data="oo";

         //   for (int i=0;i<this.PortDNS.length;i++)
         //  {
                DatagramSocket client=new DatagramSocket();
                InetAddress addr=InetAddress.getByName("127.0.0.1");
                byte[] sendbyte=new byte[1024];
                byte[] receivebyte=new byte[1024];
                sendbyte=requete.getBytes();
                DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,this.PortDNS[this.NextDns]);
                client.send(sender);
                DatagramPacket receiver=new DatagramPacket(receivebyte,receivebyte.length);
                client.receive(receiver);
                String s=new String(receiver.getData());
                Data=s.trim();

                if(Data.equals("null"))
                 {
                     this.NextDns+=1;
                     System.out.println(" Recurssif to next dns server...DNS "+ this.NextDns);
                     if(this.NextDns==this.PortDNS.length-1)
                     {this.NextDns=0;}
                     client.close();
                    return distribuer(requete);

                  }

            //}
            this.NextDns=0;
            client.close();
            System.out.println("data reçu: "+Data+": "+Data.length());
            return Data;

        }




    }




