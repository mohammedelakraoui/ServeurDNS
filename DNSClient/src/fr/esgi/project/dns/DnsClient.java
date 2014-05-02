package fr.esgi.project.dns;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.Random;

/**
 * Created by server-pc on 04/04/14.
 */
public class DnsClient {
	private static final int PORT = 53; 
	
	public byte[] dnsServerAddress = {8,8,8,8};
	
	private DatagramSocket socket;

	public DnsClient() throws SocketException{
		this.socket = new DatagramSocket();
	}
	
	public DnsClient(byte[] ip) throws SocketException{
		this();
		this.dnsServerAddress = ip;
	}
	
	//"baidu.com"
	public String getIP(String host) throws Exception{
		DnsMessage dm = new DnsMessage();
		
		dm.header = new DnsHeader();
		dm.header.qr = false;
		dm.header.rd = true;
		dm.header.qdcount = 1;
		
		dm.question = new DnsQuestion(host);
		dm.answer = new DnsResourceRecord();
		dm.authority = new DnsResourceRecord();
		dm.additional = new DnsResourceRecord();
		
		//send
		byte buffer[] = dm.getBytes();
		DatagramPacket p = new DatagramPacket(buffer, buffer.length);
		p.setAddress(Inet4Address.getByAddress(this.dnsServerAddress));
		p.setPort(PORT);
		socket.send(p);

		// receive
		byte recvBuffer[] = new byte[DnsMessage.UDP_MAX_LENGTH];
		p = new DatagramPacket(recvBuffer, recvBuffer.length);
		socket.receive(p);

		return DnsMessage.parse(new DataInputStream(new ByteArrayInputStream(recvBuffer, 0, p.getLength()))).toString();
//		return DnsMessage.parse(recvBuffer, 0, p.getLength());
	}
}