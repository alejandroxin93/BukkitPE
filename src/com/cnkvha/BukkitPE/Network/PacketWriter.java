package com.cnkvha.BukkitPE.Network;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class PacketWriter {
	private byte[] packet;
	public PacketWriter(byte pid){
		this.packet = new byte[1];
		this.packet[0] = pid;
	}
	
	public PacketWriter(int pid) {
		this.packet = new byte[1];
		this.packet[0] = (byte)(pid & 0xFF);
	}

	public void writeByte(byte value){
		this.packet = Arrays.copyOf(this.packet, this.packet.length + 1);
		this.packet[this.packet.length - 1] = value; 
	}
	
	public void writeShort(short value){
		byte[] data = new byte[2];
		data[0] = (byte)((value >> 8) & 0xFF);
		data[1] = (byte)(value & 0xFF);
		this.writeBlock(data);
	}
	
	public void writeInt(int value){
		byte[] data = new byte[4];
		data[0] = (byte)((value >> 24) & 0xFF);
		data[1] = (byte)((value >> 16) & 0xFF);
		data[2] = (byte)((value >> 8) & 0xFF);
		data[3] = (byte)(value & 0xFF);
		this.writeBlock(data);
	}
	
	public void writeLong(long value){
		byte[] data = new byte[8];
		data[0] = (byte)((value >> 56) & 0xFF);
		data[1] = (byte)((value >> 48) & 0xFF);
		data[2] = (byte)((value >> 40) & 0xFF);
		data[3] = (byte)((value >> 32) & 0xFF);
		data[4] = (byte)((value >> 24) & 0xFF);
		data[5] = (byte)((value >> 16) & 0xFF);
		data[6] = (byte)((value >> 8) & 0xFF);
		data[7] = (byte)(value & 0xFF);
		this.writeBlock(data);
	}
	
	public void writeTriad(int value){
		byte[] data = new byte[3];
		data[0] = (byte)((value >> 24) & 0xFF);
		data[1] = (byte)((value >> 16) & 0xFF);
		data[2] = (byte)((value) & 0xFF);
		this.writeBlock(data);
	}
	public void writeTriadReverse(int value){
		byte[] data = new byte[3];
		data[2] = (byte)((value >> 24) & 0xFF);
		data[1] = (byte)((value >> 16) & 0xFF);
		data[0] = (byte)((value) & 0xFF);
		this.writeBlock(data);
	}
	
	public void writeString(String value){
		if(value.length() == 0){
			this.writeShort((short) 0x00);
			return;
		}
		this.writeShort((short) (value.length() & 0xFFFF));
		this.writeBlock(value.getBytes(Charset.forName("UTF-8")));
	}
	
	public void writeFloat(float value){
		this.writeBlock(ByteBuffer.allocate(8).putFloat(value).array());
	}
	
	public void writeNullBytes(int len){
		this.writeBlock(new byte[len]);
	}
	
	public void writeBlock(byte[] block){
		if(block == null) return;
		int start = this.packet.length;
		this.packet = Arrays.copyOf(this.packet, this.packet.length + block.length);
		System.arraycopy(block, 0, this.packet, start, block.length);
	}
	
	public byte[] getPacket(){
		return(this.packet);
	}
	
}
