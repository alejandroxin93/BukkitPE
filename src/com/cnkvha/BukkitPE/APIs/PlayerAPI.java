package com.cnkvha.BukkitPE.APIs;

import java.util.HashMap;

import com.cnkvha.BukkitPE.BaseAPI;
import com.cnkvha.BukkitPE.Player;

public class PlayerAPI implements BaseAPI {
	public HashMap<String, Player> clients = new HashMap<String, Player>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return("player");
	}

	@Override
	public void init() {
		//Initialize the APi
	}
}
