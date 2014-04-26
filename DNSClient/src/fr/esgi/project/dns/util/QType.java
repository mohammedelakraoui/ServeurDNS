package fr.esgi.project.dns.util;

public class QType extends ResourceRecordType {
	/**
	 * AXFR : 252 A request for a transfer of an entire zone
	 */
	public static final short AXFR = 252;

	/**
	 * MAILB : 253 A request for mailbox-related records (MB, MG or MR)
	 */
	public static final short MAILB = 252;  
	
	/**
	 * @deprecated
	 * MAILA : 254 A request for mail agent RRs (Obsolete - see MX)
	 */
	@Deprecated
	public static final short MAILA = 254; 
	/**
	 * * : 255 A request for all records
	 */
	public static final short STAR = 255;
	
	private QType() {}
}
