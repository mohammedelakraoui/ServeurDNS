package fr.esgi.project.dns;

public abstract class DnsResourceRecord {
	public String name;
	public int type;
	public int rrClass;
	public int ttl;
	public int rdlength;
	public String rData;

	public abstract int getLength();

	public abstract byte[] getBytes();
	
	public static String parse(byte[] d){
		return null;
	}
}
