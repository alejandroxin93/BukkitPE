package com.cnkvha.BukkitPE.APIs;

import com.cnkvha.BukkitPE.APIManager;
import com.cnkvha.BukkitPE.BaseAPI;
import com.cnkvha.BukkitPE.Player;
import com.cnkvha.BukkitPE.Debugging.Log;

public class ChatAPI implements BaseAPI {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return("chat");
	}
	
	@Override
	public void init() {
		//Initialize the APi
	}
	
	public void broadcast(String msg){
		Log.Info("[Chat]" + msg);
		for(Player player : ((PlayerAPI) APIManager.get("player")).getAll()){
			player.sendChat(msg);
		}
	}
	
}
