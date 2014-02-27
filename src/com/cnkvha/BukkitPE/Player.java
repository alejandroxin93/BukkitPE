package com.cnkvha.BukkitPE;

import java.net.SocketAddress;
import java.sql.Time;
import java.util.Date;

import com.cnkvha.BukkitPE.Debugging.Log;
import com.cnkvha.BukkitPE.Utils.Definations;

public class Player {
	public SocketAddress clientAddr;
	public String ckey;
	public long cid;
	
	public String username = "";
	
	private int packetNum = 0x000000;
	public long lastRecv = 0;

	private boolean loggedIn = false;
	
	public Player(SocketAddress addr, String clientKey, long clientID){
		this.ckey = clientKey;
		this.cid = clientID;
		this.clientAddr = addr;
		this.lastRecv = new Date().getTime();
		Log.Debug("New connection from " + clientKey + ", CID=" + Long.toString(clientID) + ". ");
	}
	
	public void disconnect(String reason){
		if(reason == null) reason = "unknown";
		if(this.loggedIn){
			//TODO: Send 0x15 packet
		}
		Definations.clients.remove(this.ckey);
		Log.Info("Player " + this.username + "[" + this.clientAddr.toString() + "] left the game, reason: " + reason);
	}
	
	public void handleDataPacket(byte[] packet){
		//TODO
	}
	
}
