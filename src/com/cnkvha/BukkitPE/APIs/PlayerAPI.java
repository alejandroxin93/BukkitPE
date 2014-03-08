package com.cnkvha.BukkitPE.APIs;

import java.util.Collection;
import java.util.HashMap;

import com.cnkvha.BukkitPE.BaseAPI;
import com.cnkvha.BukkitPE.Player;

public class PlayerAPI implements BaseAPI {
	
	/*
	 * HashMap
	 * Key = SocketAddress.toString()
	 * Value = Player Class
	 */
	public HashMap<String, Player> clients = new HashMap<String, Player>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return("player");
	}

	public Player[] getAll(){
		return (Player[]) (this.clients.values().toArray());
	}
	
	@Override
	public void init() {
		//Initialize the APi
	}
}
