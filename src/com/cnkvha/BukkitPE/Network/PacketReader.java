package com.cnkvha.BukkitPE.Network;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class PacketReader {
	public byte[] packet;
	public int position;
	
	public PacketReader(byte[] packet){
		this.packet = packet;
	}
	
	public byte readByte(){
		if(this.checkLast(1) == false) return(0);
		this.position += 1;
		return(this.packet[this.position - 1]);
	}
	
	public short readShort(){
		if(this.checkLast(2) == false) return(0);
		byte[] b = new byte[2];
		b[0] = (byte) (0xFF & this.packet[this.position]);
		b[1] = (byte) (0xFF & this.packet[this.position + 1]);
		this.position += 2;
		return(ByteBuffer.wrap(b).asShortBuffer().get());
	}
	
	public String readString(){
		if(this.checkLast(2) == false) return("");
		int len = this.readShort();
		if(this.checkLast(len) == false) return("");
		String value = new String(this.readBlock(len), Charset.forName("UTF-8"));
		return(value);
	}
	
	public byte[] readBlock(int len){
		if(this.checkLast(len) == false) return(new byte[1]);
		byte[] ret = new byte[len];
		System.arraycopy(this.packet, this.position, ret, 0, len);
		return(ret);
	}
	
    public int readInt() {  
    	if(this.checkLast(4) == false) return(0);
        int firstByte = 0;  
        int secondByte = 0;  
        int thirdByte = 0;  
        int fourthByte = 0;  
        firstByte = (0x000000FF & ((int) this.packet[this.position]));  
        secondByte = (0x000000FF & ((int) this.packet[this.position + 1]));  
        thirdByte = (0x000000FF & ((int) this.packet[this.position + 2]));  
        fourthByte = (0x000000FF & ((int) this.packet[this.position + 3]));  
        this.position += 4;  
        return ((int) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFF;  
    }

	public long readLong(){
		if(this.checkLast(8) == false) return(0);
		int[] b = new int[8];
		b[0] = (0x000000FF & ((int) this.packet[this.position]));
		b[1] = (0x000000FF & ((int) this.packet[this.position + 1]));
		b[2] = (0x000000FF & ((int) this.packet[this.position + 2]));
		b[3] = (0x000000FF & ((int) this.packet[this.position + 3]));
		b[4] = (0x000000FF & ((int) this.packet[this.position + 4]));
		b[5] = (0x000000FF & ((int) this.packet[this.position + 5]));
		b[6] = (0x000000FF & ((int) this.packet[this.position + 6]));
		b[7] = (0x000000FF & ((int) this.packet[this.position + 7]));
		this.position += 8;
		return(((long) (b[0] << 56 | b[1] << 48 | b[2] << 40 | b[3] << 32 | b[4] << 24 | b[5] << 16 | b[6] << 8 | b[7] )) & 0xFFFFFFFFL);
	}
	
	public int readTriad(){
		if(this.checkLast(3) == false) return(0);
		byte[] b = new byte[3];
		b[0] = (byte) (0xFF & (this.packet[this.position]));
		b[1] = (byte) (0xFF & (this.packet[this.position + 1]));
		b[2] = (byte) (0xFF & (this.packet[this.position] + 2));
		this.position += 3;
		return((int)(b[0] << 16 | b[1] << 8 | b[2]) & 0xFFFF);
	}
	public int readTriadReverse(){
		if(this.checkLast(3) == false) return(0);
		byte[] b = new byte[3];
		b[2] = (byte) (0xFF & (this.packet[this.position]));
		b[1] = (byte) (0xFF & (this.packet[this.position + 1]));
		b[0] = (byte) (0xFF & (this.packet[this.position] + 2));
		this.position += 3;
		return((int)(b[2] << 16 | b[1] << 8 | b[0]) & 0xFFFF);
	}
    
	public float readFloat(){
		if(this.checkLast(8) == false) return(0.0f);
		byte[] b = new byte[8];
		b[0] = (byte) (0xFF & (this.packet[this.position]));
		b[1] = (byte) (0xFF & (this.packet[this.position + 1]));
		b[2] = (byte) (0xFF & (this.packet[this.position] + 2));
		b[3] = (byte) (0xFF & (this.packet[this.position] + 3));
		b[4] = (byte) (0xFF & (this.packet[this.position] + 4));
		b[5] = (byte) (0xFF & (this.packet[this.position] + 5));
		b[6] = (byte) (0xFF & (this.packet[this.position] + 6));
		b[7] = (byte) (0xFF & (this.packet[this.position] + 7));
		this.position += 8;
		return(ByteBuffer.wrap(b).asFloatBuffer().get());
	}
	
	public boolean checkLast(long last){
		if(this.packet.length - this.position < last || last < 0){
			return(false);
		}else{
			return(true);
		}
	}
	
	public byte[] getPacket(){
		return(this.packet);
	}
	
}
