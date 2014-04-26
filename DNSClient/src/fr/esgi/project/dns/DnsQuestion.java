package fr.esgi.project.dns;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import fr.esgi.project.dns.util.ResourceRecordClass;
import fr.esgi.project.dns.util.ResourceRecordType;

public class DnsQuestion {
	private byte[] qName =  null;
	public short qType;
	public short qClass;

	private String host;

	public DnsQuestion(String h) throws IOException {
		this(h, ResourceRecordType.A, ResourceRecordClass.IN);
	}
	
	public DnsQuestion(String h, short qt, short qc) throws IOException {
		this.host = h;
		this.qType = qt;
		this.qClass = qc;
		this.qName = this.generateQName();
	}
	
	public byte[] getQName(){
		return this.qName;
	}
	
	public int getLength(){
		return this.qName.length + 4;
	}
	
	private byte[] generateQName() throws IOException{
		String[] hostParts = this.host.split("\\.");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		for (String s: hostParts) {
			dos.writeByte(s.length());
			for (byte b: s.getBytes()) {
				dos.writeByte(b);
			}
		}
		dos.writeByte(0);
		dos.close();
		return baos.toByteArray();
	}
	
	public byte[] getBytes(){
		ByteBuffer bb = ByteBuffer.allocate(this.getLength());
		bb.put(this.qName);
		bb.putShort(this.qType);
		bb.putShort(this.qClass);
		return bb.array();
	}
}
