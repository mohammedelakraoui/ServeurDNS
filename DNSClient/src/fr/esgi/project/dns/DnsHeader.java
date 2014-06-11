package fr.esgi.project.dns;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import fr.esgi.project.dns.util.OpCode;
import fr.esgi.project.dns.util.RCode;;

public class DnsHeader {
	public static final int LENGTH = 12;

	public short id = 0;
	/**
	 * if(QR == false){ message is query }
	 * <br />
	 * if(QR == true) { message is response }
	 */
	public boolean qr = false;
	/**
	 * Authoritative Answer - this bit is valid in responses, <br />
	 * and specifies that the responding name server is an <br />
     * authority for the domain name in question section.
	 */
	public boolean aa = false;
	/**
	 * TrunCation - specifies that this message was truncated <br /> 
	 * due to length greater than that permitted <br />
	 * on the transmission channel.
	 */
	public boolean tc = false;
	/**
	 * Recursion Desired - this bit may be set in a query and is copied into the response. <br />
	 * If RD is set, it directs the name server to pursue the query recursively. <br />
	 * Recursive query support is optional.
	 */
	public boolean rd = false;
	/**
	 * Recursion Available - this be is set or cleared in a response, <br /> 
	 * and denotes whether recursive query support is available in the name server.
	 */
	public boolean ra = false;
	public int qdcount, ancount, nscount, arcount;
	public byte opcode;
	public byte rcode;
	
	//ID
	private byte[] generateID() {
		Random r = new Random();
		byte[] ID = new byte[2];
		r.nextBytes(ID);
		return ID;
	}
	
	private int generateQR() {
		//QR if(query) 0x0000; if(response) 0x8000
		return this.qr ? 0x8000 : 0x0000;
	}
	
	private int generateOpcode() {
		if(OpCode.IQUERY == this.opcode)
			return 0x0800;
		else if(OpCode.STATUS == this.opcode)
			return 0x1000;
		else
			return 0x0000;
	}

	private int generateAA() {
		//AA = Authoritative Answer (0x0000 | 0x0400)
		//AA is valid in response
		return this.aa && this.qr ? 0x0400 : 0x0000;
	}
	
	private int generateTC() {
		//TC = TrunCation if(message not truncated)0x0000; else 0x0200;
		return this.tc ? 0x0200 : 0x0000;
	}
	
	private int generateRD() {
		//RD = Recursion Desired (0x0000 | 0x0100)
		return this.rd ? 0x0100 : 0x0000;
	}
	
	private int generateRA() {
		//RA = Recursion Available (0x0000 | 0x0080)
		//RA is valid in response
		return this.ra && this.qr ? 0x0080 : 0x0000;
	}
	
	private int generateZ() {
		//Z always 0x0000
		return 0x0000;
	}
	
	private int generateRcode() {
		if(this.qr) {
			if(RCode.NO_ERROR_CONDITION == this.rcode)
				return 0x0000;
			else if(RCode.FORMAT_ERROR == this.rcode)
				return 0x0001;
			else if(RCode.SERVER_FAILLURE == this.rcode)
				return 0x0002;
			else if(RCode.NAME_ERROR == this.rcode)
				return 0x0003;
			else if(RCode.NOT_IMPLEMENTED == this.rcode)
				return 0x0004;
			else if(RCode.REFUSED == this.rcode)
				return 0x0005;
		}
		return 0x0000;
	}
	
	public byte[] getBytes(){
		ByteBuffer header = ByteBuffer.allocate(LENGTH);
		header.put(0 == this.id ? this.generateID() : new byte[]{(byte)(this.id & 0x00FF),(byte)((this.id & 0xFF00)>>8)});
		short s = (short)(this.generateQR()+this.generateOpcode()+this.generateAA()+this.generateTC()+this.generateRD()+this.generateRA()+this.generateZ()+this.generateRcode());
		header.putShort(s);
		header.putShort((short)Math.abs(this.qdcount));
		header.putShort((short)Math.abs(this.ancount));
		header.putShort((short)Math.abs(this.nscount));
		header.putShort((short)Math.abs(this.arcount));
		return header.array();
	}
	
	public static DnsHeader parse(byte[] data){
		DnsHeader h = new DnsHeader();
		h.id = (short)(((data[0] & 0xff) << 8) | (data[1] & 0xff));
		h.qr = (data[2] >> 7) == 1;
		h.opcode = (byte)((data[2] & 0x78) >> 3);
		h.aa = ((data[2] & 0x04) >> 2) == 1;
		h.tc = ((data[2] & 0x02) >> 1) == 1;
		h.rd = (data[2] & 0x01) == 1;
		h.ra = (data[3] >> 7) == 1;
		h.rcode = (byte)(data[3] & 0x0f);
		h.qdcount = (short)((data[4] & 0xff << 8) | (data[5] & 0xff));
		h.ancount = (short)((data[6] & 0xff << 8) | (data[7] & 0xff));
		h.nscount = (short)((data[8] & 0xff << 8) | (data[9] & 0xff));
		h.arcount = (short)((data[10] & 0xff << 8) | (data[11] & 0xff));
		return h;
	}
	
	public static DnsHeader parse(DataInputStream dis) throws IOException{
		DnsHeader h = new DnsHeader();
		h.id = dis.readShort();

		byte b = dis.readByte();
		h.qr = (b >> 7) == 1;
		h.opcode = (byte)((b & 0x78) >> 3);
		h.aa = ((b & 0x04) >> 2) == 1;
		h.tc = ((b & 0x02) >> 1) == 1;
		h.rd = (b & 0x01) == 1;

		b = dis.readByte();
		h.ra = (b >> 7) == 1;
		h.rcode = (byte)(b & 0x0f);
		
		h.qdcount = dis.readShort();
		h.ancount = dis.readShort();
		h.nscount = dis.readShort();
		h.arcount = dis.readShort();
		return h;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("HEADER { ID : "+this.id+" } ");
		sb.append("{ ANCOUNT : "+this.ancount+" } ");
		sb.append("{ NSCOUNT : "+this.nscount+" } ");
		sb.append("{ ARCOUNT : "+this.arcount+" } ");
		return sb.toString();
	}
}