package fr.esgi.project.dns;

import java.nio.ByteBuffer;
import java.util.Random;

public class Header {

	private enum OpCode { QUERY, IQUERY, STATUS; }
	private enum RCode { NO_ERROR_CONDITION, SERVER_FAILLURE, FORMAT_ERROR, NAME_ERROR, NOT_IMPLEMENTED, REFUSED}
	
	public boolean qr, aa, tc, rd, ra;
	public int qdcount, ancount, nscount, arcount;
	public OpCode opcode;
	public RCode rcode;
	
	//ID
	private byte[] generateID(){
		Random r = new Random();
		byte[] ID = new byte[2];
		r.nextBytes(ID);
		return ID;
	}
	
	private int generateQR() {
		//QR if(query) 0x0000; if(response) 0x8000
		return this.qr ? 0x0000 : 0x8000;
	}
	
	private int generateOpcode() {
		if(OpCode.QUERY == this.opcode)
			return 0x0000;
		else if(OpCode.IQUERY == this.opcode)
			return 0x0800;
		else 
			return 0x1000;
	}

	//AA = Authoritative Answer (0x0000 | 0x0400)
	//AA is valid in response
	private int generateAA() {
		return this.aa ? 0x0400 : 0x0000;
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
		return this.ra ? 0x0080 : 0x0000;
	}
	
	private int generateZ() {
		//Z always 0x0000
		return 0x0000;
	}
	
	private int generateRcode() {
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
		else 
			return 0x0000;
	}
	
	public byte[] getBytes(){
		
		ByteBuffer header = ByteBuffer.allocate(12);
		header.put(this.generateID());
		header.putInt(this.generateQR()+this.generateOpcode()+this.generateAA()+this.generateTC()+this.generateRD()+this.generateRA()+this.generateZ()+this.generateRcode());
		header.putInt(this.qdcount);
		header.putInt(this.ancount);
		header.putInt(this.nscount);
		header.putInt(this.arcount);
		return header.array();
	}
}
