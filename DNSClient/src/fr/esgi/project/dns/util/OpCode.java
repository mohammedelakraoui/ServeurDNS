package fr.esgi.project.dns.util;

public class OpCode {
	/**
	 * A standard query
	 */
	public static final byte QUERY = 0;
	
	/**
	 * An inverse query 
	 */
	public static final byte IQUERY = 1;
	
	/**
	 * A server status request
	 */
	public static final byte STATUS = 2;
	
	private OpCode(){}
}
