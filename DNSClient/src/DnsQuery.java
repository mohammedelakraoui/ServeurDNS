import java.nio.ByteBuffer;
import java.util.Random;


public class DnsQuery {
	public DnsQuery(){}
	
	public void buildHeader(){
		Random r = new Random();
		ByteBuffer b = ByteBuffer.allocate(2);
		//ID
		b.putInt(r.nextInt(16));
		byte[] ID = b.array();
		//QR if(query) 0x0000; if(response) 0x8000 
		int qr = 0x0000;
		//Opcode
		int Opcode = 0x0000;
		//AA = Authoritative Answer (0x0000 | 0x0400)
		int aa = 0x0000;
		//TC = TrunCation if(message not truncated)0x0000; else 0x0200; 
		int tc = 0x0000;
		//RD = Recursion Desired (0x0000 | 0x0100)
		int rd = 0x0000;
		//RA = Recursion Available (0x0000 | 0x0080)
		int ra = 0x0000;
		//Z always 0x0000
		int z = 0x0000;
		//RCODE
		int rcode = 0x0000;
	}
}
