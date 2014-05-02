package fr.esgi.project.dns.util;

import java.io.DataInputStream;

public class Tools {

	public static String readDomainName(byte[] data, int offset, int length) {
		StringBuffer strBuf = new StringBuffer();
		int i = offset;
		do
		{
			// read character count
			byte count = data[i++];
			
			if (count == 0)
				break;
			if (strBuf.length() > 0)
				strBuf.append('.');
			while (count-- > 0) {
				byte c = data[i++];
				strBuf.append((char)c);
			}
		}
		while(i < length);
		
		while(0 != data[i++]);
		
		return strBuf.toString();
	}

	public static String readDomainName(DataInputStream dis, byte firstCount) throws Exception {
		StringBuffer strBuf = new StringBuffer();
		while (true) {
			// read character count
			byte count = 0;
			if (firstCount != 0) {
				count = firstCount;
				firstCount = 0;
			} else 
				count = dis.readByte();
			
			if (count == 0)
				break;
			if (strBuf.length() > 0)
				strBuf.append('.');
			while (count-- > 0) {
				byte c = dis.readByte();
				strBuf.append((char)c);
			}
		}
		
		byte length;
		while(0 != (length = dis.readByte())){
			
		}
		return strBuf.toString();
	}
	
	public static String readDomainName(DataInputStream dis) throws Exception {
		StringBuffer strBuf = new StringBuffer();
		byte count = 0;
		do{
			count = dis.readByte();
			if (strBuf.length() > 0)
				strBuf.append('.');
			while (count-- > 0) {
				System.out.println(count);
				byte c = dis.readByte();
				strBuf.append((char)c);
			}
		}while(0 == count);
		byte length;
		while(0 != dis.readByte());
		return strBuf.toString();
	}
}
