package com.cnkvha.BukkitPE.EventSystem;

import java.util.HashMap;

public class DefaultEvents {
	public void registerAllDefaultEvents(){
		EventSystem.registerEvent("player.chat", EventPriority.NORMAL, this, "onPlayerChat");
	}
	
	public int onPlayerChat(HashMap<String, Object> data){
		
		return(CancelStatus.NOT_CANCELLED);
	}
}
