package com.cnkvha.BukkitPE;

import java.net.SocketAddress;
import java.sql.Time;
import java.util.Date;

import com.cnkvha.BukkitPE.Debugging.Log;

public class Player {
	public SocketAddress clientAddr;
	public String ckey;
	public long cid;
	
	public String username = "";
	
	private int packetNum = 0x000000;
	private long lastRecv = 0;
	
	public Player(SocketAddress addr, String clientKey, long clientID){
		this.ckey = clientKey;
		this.cid = clientID;
		this.clientAddr = addr;
		this.lastRecv = new Date().getTime();
		Log.Debug("New connection from " + clientKey + ", CID=" + Long.toString(clientID) + ". ");
	}
	
	public void handleDataPacket(byte[] packet){
		//TODO
	}
	
}
