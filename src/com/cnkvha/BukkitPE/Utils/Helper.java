package com.cnkvha.BukkitPE.Utils;

import java.net.SocketAddress;

public class Helper {
	public static String toHex(byte[] digest) {
	    StringBuilder sb = new StringBuilder();
	    for (byte b : digest) {
	        sb.append(String.format("%1$02X", b) + " ");
	    }
	    return sb.toString();
	}
	
	public static String getClientKey(SocketAddress addr){
		if(addr == null) return(null);
		return(addr.toString());
	}
	
	
}
