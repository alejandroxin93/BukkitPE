package com.cnkvha.BukkitPE.Network.EventSystem;

import java.net.DatagramPacket;
import java.util.EventObject;

public class UDPRecvEvent extends EventObject {
	private static final long serialVersionUID = -4182501186470307242L;
	private DatagramPacket pk;
	public UDPRecvEvent(DatagramPacket packet) {
		super((Object)packet);
		this.pk = packet;
	}
	
	public DatagramPacket getPacket(){
		return(this.pk);
	}
	
	public byte[] getData(){
		byte[] packet = new byte[this.pk.getLength()];
		System.arraycopy(this.pk.getData(), 0, packet, 0, this.pk.getLength());
		return(packet);
	}
	
}
