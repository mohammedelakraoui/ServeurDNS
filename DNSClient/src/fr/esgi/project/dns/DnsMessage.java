package fr.esgi.project.dns;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

//labels          63 octets or less
//names           255 octets or less
//TTL             positive values of a signed 32 bit number.
//UDP messages    512 octets or less

public class DnsMessage {
	public static final int UDP_MAX_LENGTH = 512;
	
	public DnsHeader header;
	public DnsQuestion question;
	public List<DnsResourceRecord> answer;
	public List<DnsResourceRecord> authority;
	public List<DnsResourceRecord> additional;
	
	public int getlength(){
		int length = 0;
		if(null != this.header){
			length += length = DnsHeader.LENGTH;
			if(!this.header.qr && null != this.question)
				length += this.question.getLength();
			else if(this.header.qr && null != this.answer)
				for(DnsResourceRecord rr : answer)
					length += rr.getLength();
		}
		return length;
	}
	
	public byte[] getBytes(){
		int length = this.getlength();
		ByteBuffer bb = ByteBuffer.allocate(length);
		if(length > 0){
			bb.put(this.header.getBytes());
			bb.put(this.question.getBytes());
			if(null != this.answer)
				for(DnsResourceRecord rr : this.answer)
					bb.put(rr.getBytes());
			if(null != this.authority)
				for(DnsResourceRecord rr : this.authority)
					bb.put(rr.getBytes());
			if(null != this.additional)
				for(DnsResourceRecord rr : this.additional)
					bb.put(rr.getBytes());
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
		dm.answer = new ArrayList<DnsResourceRecord>();
		for (int i = 0; i < dm.header.ancount; i++) {
			dm.answer.add(DnsResourceRecord.parse(dis));
		}
		dm.authority = new ArrayList<DnsResourceRecord>();
		for (int i = 0; i < dm.header.nscount; i++) {
			dm.authority.add(DnsResourceRecord.parse(dis));
		}
		dm.additional = new ArrayList<DnsResourceRecord>();
		for (int i = 0; i < dm.header.arcount; i++) {
			dm.additional.add(DnsResourceRecord.parse(dis));
		}
		return dm;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.header.toString());
		sb.append(System.lineSeparator());
		sb.append(this.question.toString());
		sb.append(System.lineSeparator());
		if(null != this.answer){
			sb.append("ANSWER");
			sb.append(System.lineSeparator());
			for(DnsResourceRecord rr : this.answer) {
				sb.append('\t');
				sb.append(rr.toString());
				sb.append(System.lineSeparator());
			}
		}
		if(null != this.authority){
			sb.append("AUTHORITY");
			sb.append(System.lineSeparator());
			for(DnsResourceRecord rr : this.authority) {
				sb.append('\t');
				sb.append(rr.toString());
				sb.append(System.lineSeparator());
			}
		}
		if(null != this.additional){
			sb.append("ADDITIONAL");
			sb.append(System.lineSeparator());
			for(DnsResourceRecord rr : this.additional) {
				sb.append('\t');
				sb.append(rr.toString());
			}
		}
		return sb.toString();
	}
}