package com.cnkvha.BukkitPE.Utils;

public class Helper {
	public static String toHex(byte[] digest) {
	    StringBuilder sb = new StringBuilder();
	    for (byte b : digest) {
	        sb.append(String.format("%1$02X", b) + " ");
	    }
	    return sb.toString();
	}
}
