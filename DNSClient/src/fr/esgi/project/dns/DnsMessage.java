package fr.esgi.project.dns;

import java.nio.ByteBuffer;

//labels          63 octets or less
//names           255 octets or less
//TTL             positive values of a signed 32 bit number.
//UDP messages    512 octets or less

public class DnsMessage {
	public static final int UDP_MAX_LENGTH = 512;
	
	public DnsHeader header;
	public DnsQuestion question;
	public DnsAnswer answer;
	public DnsAuthority authority;
	public DnsAdditional additional;
	
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
			bb.put(this.answer.getBytes());
			bb.put(this.authority.getBytes());
			bb.put(this.additional.getBytes());
		}
		return bb.array();
	}

	public static String parse(byte[] data){
		return null;
	}
	
	public static String parse(byte[] data, int offset, int length){
		return null;
	}
}
