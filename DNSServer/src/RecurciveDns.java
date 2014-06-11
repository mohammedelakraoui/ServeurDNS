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
                System.out.println("Tram du client est : "+req);
                InetAddress addr=receiver.getAddress();
                int port=receiver.getPort();
                System.out.println("Waiting response from dns server ...");
                sendbyte=dnsserver.Distribuer(req).getBytes();
                // Reponse to http server
                System.out.println("Send data to client !!!");
                DatagramPacket sender=new DatagramPacket(sendbyte,sendbyte.length,addr,port);
                server.send(sender);

            }

        }


        private  String Distribuer(String requete) throws IOException {
                 String Data=null;

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
                client.close();
                if(Data.equals("null"))
                 {
                     System.out.println(" Recurcif to next dns server...DNS "+ this.NextDns);
                     if(this.NextDns==this.PortDNS.length-1)
                     {this.NextDns=0;}
                     else{this.NextDns++;}
                     Distribuer(requete);

                  }
               this.NextDns=0;
            //}
            return Data;

        }




    }




