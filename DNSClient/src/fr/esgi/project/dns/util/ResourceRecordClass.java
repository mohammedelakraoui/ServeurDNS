package fr.esgi.project.dns.util;

public class ResourceRecordClass {
	/**
	 * IN : 1 the Internet
	 */
	public static final short IN = 1; 

	/**
	 * @deprecated
	 * CS : 2 the CSNET class (Obsolete - used only for examples in some obsolete RFCs)
	 */
	@Deprecated
	public static final short CS = 2;

	/**
	 * CH : 3 the CHAOS class
	 */
	public static final short CH = 3;

	/**
	 * HS : 4 Hesiod [Dyer 87]
	 */
	public static final short HS = 4;
	
	protected ResourceRecordClass(){}
}
