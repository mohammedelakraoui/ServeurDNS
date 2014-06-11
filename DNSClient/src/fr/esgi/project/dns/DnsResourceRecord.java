package fr.esgi.project.dns;

import java.io.DataInputStream;
import java.io.IOException;

import fr.esgi.project.dns.util.Tools;

public class DnsResourceRecord {
	public String name;
	public int type;
	public int rrClass;
	public int ttl;
	public int rdlength;
	public byte[] rData;
	
	private int pointerPosition;

	public int getLength(){
		return 0;
	}

	public byte[] getBytes(){
		return null;
	}
	
	public static DnsResourceRecord parse(byte[] data, int offset, int length){
		return null;
	}

	public static DnsResourceRecord parse(DataInputStream dis) throws Exception{
		DnsResourceRecord rr = new DnsResourceRecord();
		// read domain name
		byte firstCount = dis.readByte();
		if (0 != (firstCount & 0xC0)) {
			// this is a pointer
			rr.pointerPosition = firstCount & 0x3F;
			// read an ending 0
			byte ending = dis.readByte();
			rr.pointerPosition = (rr.pointerPosition << 8) | ending; 
		} else {
			// this is a normal name
			rr.name = Tools.readDomainName(dis, firstCount); 
		}
		
		// read query type and query class
		rr.type = dis.readShort();
		rr.rrClass = dis.readShort();
		rr.ttl = dis.readInt();
		rr.rdlength = dis.readShort();
		if (rr.rdlength > 0) {
			rr.rData = new byte[rr.rdlength];
			dis.read(rr.rData, 0, rr.rData.length);
		}
		return rr;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("rr { NAME : "+this.name+" } ");
		sb.append("{ TYPE : "+this.type+" } ");
		sb.append("{ CLASS : "+this.rrClass+" } ");
		sb.append("{ TTL : "+this.ttl+" } ");
		sb.append("{ RDLNEGTH : "+this.rdlength+" } ");
		sb.append("{ RDATA : "+this.rData+" } ");
		// ip address
		sb.append("address IP : ");
		sb.append((int)this.rData[0]&0xFF);
		sb.append('.');
		sb.append((int)this.rData[1]&0xFF);
		sb.append('.');
		sb.append((int)this.rData[2]&0xFF);
		sb.append('.');
		sb.append((int)this.rData[3]&0xFF);
		return sb.toString();
	}
}