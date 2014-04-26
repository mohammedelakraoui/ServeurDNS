package fr.esgi.project.dns.util;

public class ResourceRecordType {
	/**
	 * A : 1 a host address
	 */
	public static final short A = 1;
	
	/**
	 * NS : 2 an authoritative name server
	 */
	public static final short NS = 2;
	
	/**
	 * @deprecated
	 * MD : 3 a mail destination (Obsolete - use MX)
	 */
	@Deprecated
	public static final short MD = 3;
	
	/**
	 * @deprecated
	 * MF : 4 a mail forwarder (Obsolete - use MX)
	 */
	@Deprecated
	public static final short MF = 4;
	
	/**
	 * CNAME : 5 the canonical name for an alias
	 */
	public static final short CNAME = 5;
	
	/**
	 * SOA : 6 marks the start of a zone of authority
	 */
	public static final short SOA = 6;
	
	/**
	 * MB : 7 a mailbox domain name (EXPERIMENTAL)
	 */
	public static final short MB = 7;
	
	/**
	 * MG : 8 a mail group member (EXPERIMENTAL)
	 */
	public static final short MG = 8;
	
	/**
	 * MR : 9 a mail rename domain name (EXPERIMENTAL)
	 */
	public static final short MR = 9;
	
	/**
	 * NULL : 10 a null RR (EXPERIMENTAL)
	 */
	public static final short NULL = 10;
	
	/**
	 * WKS : 11 a well known service description
	 */
	public static final short WKS = 11;
	
	/**
	 * PTR : 12 a domain name pointer
	 */
	public static final short PTR = 12;
	
	/**
	 * HINFO : 13 host information
	 */
	public static final short HINFO = 13;
	
	/**
	 * MINFO : 14 mailbox or mail list information
	 */
	public static final short MINFO = 14;
	
	/**
	 * MX : 15 mail exchange
	 */
	public static final short MX = 15;
	
	/**
	 * TXT : 16 text strings
	 */
	public static final short TXT = 16;
	
	protected ResourceRecordType(){}
}