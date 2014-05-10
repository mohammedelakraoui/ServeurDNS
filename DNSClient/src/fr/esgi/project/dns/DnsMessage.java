package fr.esgi.project.dns;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

//labels          63 octets or less
//names           255 octets or less
//TTL             positive values of a signed 32 bit number.
//UDP messages    512 octets or less

public class DnsMessage {
	public static final int UDP_MAX_LENGTH = 512;
	
	public DnsHeader header;
	public DnsQuestion question;
	public DnsResourceRecord answer;
	public DnsResourceRecord authority;
	public DnsResourceRecord additional;
	
	public int getlength(){
		int length = 0;
		if(null != this.header){
			length += length = DnsHeader.LENGTH;
			if(!this.header.qr && null != this.question)
				length += this.question.getLength();
			else if(this.header.qr && null != this.answer)
				length += this.answer.getLength();
		}
		return length;
	}
	
	public byte[] getBytes(){
		int length = this.getlength();
		ByteBuffer bb = ByteBuffer.allocate(length);
		if(length > 0){
			bb.put(this.header.getBytes());
			bb.put(this.question.getBytes());
//			bb.put(this.answer.getBytes());
//			bb.put(this.authority.getBytes());
//			bb.put(this.additional.getBytes());
		}
		return bb.array();
	}

	public static String parse(byte[] data) throws IOException{
		return parse(data, 0, data.length);
	}
	
	public static String parse(byte[] data, int offset, int length){
		DnsHeader h = DnsHeader.parse(data);
//		for (int i = 0; i < h.qdcount; i++) {
			DnsQuestion.parse(data, 12, length);
//		}
		return null;
	}
	
	public static DnsMessage parse(DataInputStream dis) throws Exception{
		DnsMessage dm = new DnsMessage();
		dm.header = DnsHeader.parse(dis);
//		for (int i = 0; i < h.qdcount; i++) {
			dm.question = DnsQuestion.parse(dis);
//		}
//		for (int i = 0; i < h.ancount; i++) {
			dm.answer = DnsResourceRecord.parse(dis); 
//		}
//		for (int i = 0; i < h.nscount; i++) {
			dm.authority = DnsResourceRecord.parse(dis);
//		}
//		for (int i = 0; i < h.arcount; i++) {
			dm.additional = DnsResourceRecord.parse(dis);
//		}
		return dm;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.header.toString());
		sb.append(this.question.toString());
		sb.append(this.answer.toString());
		sb.append(this.authority.toString());
		sb.append(this.additional.toString());
		return sb.toString();
	}
}
