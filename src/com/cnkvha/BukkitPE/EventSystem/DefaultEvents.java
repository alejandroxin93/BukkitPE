package com.cnkvha.BukkitPE.EventSystem;

import java.util.HashMap;

import com.cnkvha.BukkitPE.APIManager;
import com.cnkvha.BukkitPE.APIs.*;

public class DefaultEvents {
	public void registerAllDefaultEvents(){
		EventSystem.registerEvent("player.chat", EventPriority.NORMAL, this, "onPlayerChat");
	}
	
	public int onPlayerChat(HashMap<String, Object> data){
		((ChatAPI) APIManager.get("chat")).broadcast( "<" + ((String)data.get("sender")) + "> " + ((String) data.get("message"))  );
		return(CancelStatus.NOT_CANCELLED);
	}
}
