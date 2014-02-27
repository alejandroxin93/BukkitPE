package com.cnkvha.BukkitPE;

import java.net.SocketAddress;
import java.sql.Time;
import java.util.Date;

import com.cnkvha.BukkitPE.APIs.PlayerAPI;
import com.cnkvha.BukkitPE.Debugging.Log;
import com.cnkvha.BukkitPE.Network.PacketReader;
import com.cnkvha.BukkitPE.Network.PacketWriter;
import com.cnkvha.BukkitPE.Utils.Definations;
import com.sun.java.util.jar.pack.PackerImpl;

public class Player {
	public SocketAddress clientAddr;
	public String ckey;
	public long cid;
	public long session = 0x0000000000000000;
	
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
		PlayerAPI playerAPI= (PlayerAPI)APIManager.get("player");
		playerAPI.clients.remove(this.ckey);
		Log.Info("Player " + this.username + "[" + this.clientAddr.toString() + "] left the game, reason: " + reason);
	}
	
	public void handleDataPacket(byte[] packet){
		PacketReader reader = new PacketReader(packet);
		reader.readByte(); //Skip PID section
		this.packetNum = reader.readTriad();
		this.sendACK(this.packetNum); //Send ACK
		byte encapsulateType = reader.readByte();
		int len = 0;
		byte[] customPacket;
		switch(encapsulateType){
		case 0x00:
			while(true){
				len = reader.readShort();
				if(len == 0) break;
				len = len / 8;
				customPacket = reader.readBlock(len);
				this.handleCustomPacket(customPacket);
			}
			break;
		}
	}
	
	public void handleCustomPacket(byte[] packet){
		PacketReader reader = new PacketReader(packet);
		PacketWriter response;
		byte pid = reader.readByte();
		Log.Debug("Got encapsulated packet: 0x" + Integer.toHexString((int)pid));
		switch(pid){
		case 0x09:
			this.cid = reader.readLong();
			this.session = reader.readLong();
			reader.readByte(); //unknown1
			response = new PacketWriter(0x09);
			response.writeBlock(new byte[]{0x04, 0x3F, 0x57, (byte) 0xFE});
			response.writeBlock(new byte[]{(byte) 0xCD});
			response.writeBlock(new byte[]{(byte) 0xF5, (byte) 0xFF, (byte) 0xFF, 0xF5});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[0x00, 0x00]);
			response.writeLong(this.session);
			response.writeLong(this.session);
			
			//TODO: Add send encapsulated packets. 
			//this.sendEncapPacket(response);
			break;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void sendACK(int pNum){
		PacketWriter ack = new PacketWriter(0xC0);
		ack.writeShort((short) 0x01);
		ack.writeByte((byte) 0x00);
		ack.writeTriad(0x000000);  //Muhahahah!
		ack.writeTriad(pNum);
		this.sendPacket(ack);
	}
	
	
	public void sendPacket(PacketWriter pk){
		try {
			Definations.socket.sendTo(pk.getPacket(), this.clientAddr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendPacket(byte[] buffer){
		try {
			Definations.socket.sendTo(buffer, this.clientAddr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
