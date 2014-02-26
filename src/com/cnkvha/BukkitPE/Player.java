package com.cnkvha.BukkitPE;

import java.net.SocketAddress;

public class Player {
	public SocketAddress clientAddr;
	public Player(SocketAddress addr){
		this.clientAddr = addr;
	}
}
