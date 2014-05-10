package fr.esgi.project.dns.util;

public class RCode {
	/**
	 * No error condition
	 */
	public static final byte NO_ERROR_CONDITION = 0;
	
	/**
	 * Format error <br />
	 * The name server was unable to interpret the query.
	 */
	public static final byte FORMAT_ERROR = 1;
	
	/**
	 * Server failure <br />
	 * The name server was unable to process this query due to a
	 * problem with the name server.                         
	 */
	public static final byte SERVER_FAILLURE = 2;
	
	/**
	 * Name Error <br />
	 * Meaningful only for responses from an authoritative name server, <br />
	 * this code signifies that the domain name referenced in the query does not exist.
	 */
	public static final byte NAME_ERROR = 3;
	
	/**
	 * Not Implemented <br />
	 * The name server does not support the requested kind of query.
	 */
	public static final byte NOT_IMPLEMENTED = 4;
	
	/**
	 * Refused <br />
	 * The name server refuses to perform the specified operation for policy reasons. <br />
	 * For example, a name server may not wish to provide the information to the particular requester, <br />
	 * or a name server may not wish to perform a particular operation (e.g., zone transfer) for particular data.
	 */
	public static final byte REFUSED = 5;
	
	private RCode(){}
}
