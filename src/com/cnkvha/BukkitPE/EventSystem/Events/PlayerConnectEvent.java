package com.cnkvha.BukkitPE.EventSystem.Events;

import com.cnkvha.BukkitPE.EventSystem.BaseEvent;
import com.cnkvha.BukkitPE.EventSystem.CancellableEvent;

public class PlayerConnectEvent extends CancellableEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8832790877230328783L;
	private String uname = "";
	
	public PlayerConnectEvent(String username){
		if(username != null){
			this.uname = username;
		}
	}
	
	public String getUsername(){
		return(this.uname);
	}
	
}
