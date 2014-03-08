package com.cnkvha.BukkitPE;

import java.net.SocketAddress;
import java.util.Date;
import java.util.HashMap;

import com.cnkvha.BukkitPE.APIs.PlayerAPI;
import com.cnkvha.BukkitPE.Debugging.Log;
import com.cnkvha.BukkitPE.EventSystem.EventSystem;
import com.cnkvha.BukkitPE.Network.PacketReader;
import com.cnkvha.BukkitPE.Network.PacketWriter;
import com.cnkvha.BukkitPE.Utils.Definations;

public class Player {
	public SocketAddress clientAddr;
	public String ckey;
	public long cid; //Get from the initial packets, maybe is the device/client ID
	public int pcid; //Get from the encapsulated packet, maybe is the player ID
	public long session = 0x0000000000000000;
	
	public String username = "";
	
	private int packetNum = 0x000000;
	public long lastRecv = 0;
	public long lastPing = 0;

	private boolean connected = false;
	private boolean loggedIn = false;
	public String loginData;
	
	public Player(SocketAddress addr, String clientKey, long clientID){
		this.ckey = clientKey;
		this.cid = clientID;
		this.clientAddr = addr;
		this.lastRecv = new Date().getTime();
		Log.Debug("New connection from " + clientKey + ", CID=" + Long.toString(clientID) + ". ");
	}
	
	
	public void sendChat(String chat){
		PacketWriter pk = new PacketWriter(0x85);
		pk.writeString(chat);
		pk.writeString("");
		this.sendEncapPacket(pk);
	}
	
	public void disconnect(String reason){
		if(reason == null) reason = "unknown";
		if(this.loggedIn){
			PacketWriter pk = new PacketWriter(0x15);
			this.sendEncapPacket(pk);
		}
		PlayerAPI playerAPI= (PlayerAPI)APIManager.get("player");
		playerAPI.clients.remove(this.ckey);
		Log.Info("Player " + this.username + "[" + this.clientAddr.toString() + "] left the game, reason: " + reason);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void handleDataPacket(byte[] packet){
		PacketReader reader = new PacketReader(packet);
		reader.readByte(); //Skip PID section
		this.packetNum = reader.readTriadReverse() - 2;
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
		case 0x40:
			while(true){
				len = reader.readShort();
				Log.Debug("SHORT=" + len);
				reader.readTriadReverse();
				if(len == 0) break;
				len = len / 8;
				customPacket = reader.readBlock(len);
				this.handleCustomPacket(customPacket);
			}
			break;
		case 0x60:
			while(true){
				len = reader.readShort();
				reader.readTriadReverse();
				reader.readInt();
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
		case 0x00:  //Ping!
			response = new PacketWriter(0x03); //Pong!
			response.writeLong(this.lastPing);
			this.lastPing = reader.readLong();
			response.writeLong(this.lastPing);
			this.sendEncapPacket(response);
			break;
		case 0x09:
			this.cid = reader.readLong();
			this.session = reader.readLong();
			reader.readByte(); //unknown1
			response = new PacketWriter(0x10);
			response.writeBlock(new byte[]{0x04, 0x3F, 0x57, (byte) 0xFE});
			response.writeBlock(new byte[]{(byte) 0xCD});
			response.writeBlock(new byte[]{(byte) 0xF5, (byte) 0xFF, (byte) 0xFF, (byte) 0xF5});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
			response.writeBlock(new byte[]{0x00, 0x00});
			response.writeLong(this.session);
			response.writeLong(this.session);
			this.sendEncapPacket(response);
			break;
		case 0x13:
			this.connected = true;
			break;
		case (byte) 0x82:
			if(this.loggedIn) break;
			this.username = reader.readString();
			reader.readInt();
			reader.readInt();
			this.pcid = reader.readInt();
			this.loginData = reader.readString();
			response = new PacketWriter(0x83);
			if(true){//PlayerConnectEventManager.callEvent(this.username)
				response.writeInt(0x00);
				this.sendEncapPacket(response);
				response = new PacketWriter(0x87);
				response.writeInt(0x00);
				response.writeInt(0x00);
				response.writeInt(0x01);
				response.writeInt(0x00);
				response.writeFloat(128.0f);
				response.writeFloat(64.0f);
				response.writeFloat(0.0f);
				this.sendEncapPacket(response);
				this.loggedIn = true;
				Log.Info("Player " + this.username + "[" + this.clientAddr.toString() + "] joined the game. ");
				/*
			}else{
				response.writeInt(0x02);
				this.sendEncapPacket(response);
				this.disconnect("denied to join");
				*/
			}
			break;
		case (byte) 0x85:
			String msg = reader.readString();
			String sender = reader.readString();
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("sender", this.username);
			data.put("message", msg);
			EventSystem.callHandler("player.chat", data);
			//Log.Info(this.username + ": " + );
			break;
		}
	}
	
	
	
	
	
	public void sendEncapPacket(PacketWriter payload){
		PacketWriter pk = new PacketWriter(0x84);
		this.packetNum++;
		pk.writeTriadReverse(this.packetNum);
		pk.writeByte((byte) 0x00);
		pk.writeShort((short) (payload.getPacket().length * 8));
		pk.writeBlock(payload.getPacket());
		this.sendPacket(pk);
	}
	
	
	public void sendACK(int pNum){
		PacketWriter ack = new PacketWriter(0xC0);
		ack.writeShort((short) 0x01);
		ack.writeByte((byte) 0x01);
		ack.writeTriadReverse(pNum);
		this.sendPacket(ack);
	}
	
	
	public void sendPacket(PacketWriter pk){
		try {
			Definations.socket.sendTo(pk.getPacket(), this.clientAddr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void sendPacket(byte[] buffer){
		try {
			Definations.socket.sendTo(buffer, this.clientAddr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
