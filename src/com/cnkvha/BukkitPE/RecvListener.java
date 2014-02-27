package com.cnkvha.BukkitPE;

import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.net.SocketException;

import com.cnkvha.BukkitPE.APIs.PlayerAPI;
import com.cnkvha.BukkitPE.Debugging.Log;
import com.cnkvha.BukkitPE.Network.PacketReader;
import com.cnkvha.BukkitPE.Network.PacketWriter;
import com.cnkvha.BukkitPE.Network.EventSystem.UDPRecvEvent;
import com.cnkvha.BukkitPE.Network.EventSystem.UDPRecvEventListener;
import com.cnkvha.BukkitPE.Utils.Constants;
import com.cnkvha.BukkitPE.Utils.Definations;
import com.cnkvha.BukkitPE.Utils.Helper;

public class RecvListener implements UDPRecvEventListener {
	
	@Override
	public void onRecv(UDPRecvEvent event) {
		// TODO Auto-generated method stub
		DatagramPacket packet = event.getPacket();
		this.processPacket(event.getData(), event);
	}
	
	public void processPacket(byte[] packet, UDPRecvEvent event){
		PacketReader reader = new PacketReader(packet);
		PacketWriter response;
		int pid = reader.readByte() & 0xFF;
		Log.Debug("Got packet 0x" + Integer.toHexString(pid));
		switch(pid){
		case 0x01:
			long pingID = reader.readLong();
			Log.Debug("Got UNCONECTED_PING, timestamp: " + pingID);
			response = new PacketWriter(0x1C);
			response.writeLong(pingID);
			response.writeLong(Constants.SERVERID);
			response.writeBlock(Constants.MAGIC);
			response.writeString("MCCPP;Demo;Test");
			this.sendData(response.getPacket(), event.getPacket().getSocketAddress());
			break;
		case 0x05:
			response = new PacketWriter(0x06);
			response.writeBlock(Constants.MAGIC);
			response.writeLong(Constants.SERVERID);
			response.writeByte((byte) 0x00);
			response.writeShort((short) 1155);
			this.sendData(response.getPacket(), event.getPacket().getSocketAddress());
			break;
		case 0x07:
			reader.readBlock(16);
			reader.readBlock(5);
			int port = (int)reader.readShort();
			reader.readShort();
			long cid = reader.readLong();
			if(!(((PlayerAPI)APIManager.get("player")).clients.containsKey(Helper.getClientKey(event.getPacket().getSocketAddress())))){
				Player player = new Player(event.getPacket().getSocketAddress(), Helper.getClientKey(event.getPacket().getSocketAddress()), cid);
				((PlayerAPI)APIManager.get("player")).clients.put(Helper.getClientKey(event.getPacket().getSocketAddress()), player);
			}
			response = new PacketWriter(0x08);
			response.writeBlock(Constants.MAGIC);
			response.writeLong(Constants.SERVERID);
			response.writeShort((short) event.getPacket().getPort());
			response.writeShort((short) 1155);
			response.writeByte((byte) 0x00);
			this.sendData(response.getPacket(), event.getPacket().getSocketAddress());
			break;
		case 0x80:
		case 0x81:
		case 0x82:
		case 0x83:
		case 0x84:
		case 0x85:
		case 0x86:
		case 0x87:
		case 0x88:
		case 0x89:
		case 0x8A:
		case 0x8B:
		case 0x8C:
		case 0x8D:
		case 0x8E:
		case 0x8F:
			if(!(((PlayerAPI)APIManager.get("player")).clients.containsKey(event.getPacket().getSocketAddress().toString()))) return;
			((PlayerAPI)APIManager.get("player")).clients.get(event.getPacket().getSocketAddress().toString()).handleDataPacket(event.getData());
			break;
		}
	}
	
	public void sendData(byte[] packet, SocketAddress addr){
		try {
			Definations.socket.sendTo(packet, addr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
