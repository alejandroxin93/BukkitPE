package com.cnkvha.BukkitPE.EventSystem.Managers;

import java.util.Enumeration;

import com.cnkvha.BukkitPE.EventSystem.*;
import com.cnkvha.BukkitPE.EventSystem.Events.PlayerConnectEvent;
import com.cnkvha.BukkitPE.EventSystem.Interfaces.PlayerConnectListener;

public class PlayerConnectEventManager extends BaseManager {
	public static boolean callEvent(String username){
		Enumeration enumeration;
		PlayerConnectListener listener;
		PlayerConnectEvent event = new PlayerConnectEvent(username);
		enumeration = MonitorHandlers.elements();
		while(enumeration.hasMoreElements()){
			listener = (PlayerConnectListener)enumeration.nextElement();
			listener.onTrigger(event);
		}
		event.cancelStatus = CancelStatus.NOT_CANCELLED;
		enumeration = lv4Handlers.elements();
		while(enumeration.hasMoreElements()){
			if(event.cancelStatus == CancelStatus.FORCE_CANCELLED) return(false);
			listener = (PlayerConnectListener)enumeration.nextElement();
			listener.onTrigger(event);
		}
		enumeration = lv3Handlers.elements();
		while(enumeration.hasMoreElements()){
			if(event.cancelStatus == CancelStatus.FORCE_CANCELLED) return(false);
			listener = (PlayerConnectListener)enumeration.nextElement();
			listener.onTrigger(event);
		}
		enumeration = lv2Handlers.elements();
		while(enumeration.hasMoreElements()){
			if(event.cancelStatus == CancelStatus.FORCE_CANCELLED) return(false);
			listener = (PlayerConnectListener)enumeration.nextElement();
			listener.onTrigger(event);
		}
		enumeration = lv1Handlers.elements();
		while(enumeration.hasMoreElements()){
			if(event.cancelStatus == CancelStatus.FORCE_CANCELLED) return(false);
			listener = (PlayerConnectListener)enumeration.nextElement();
			listener.onTrigger(event);
		}
		enumeration = lv0Handlers.elements();
		while(enumeration.hasMoreElements()){
			if(event.cancelStatus == CancelStatus.FORCE_CANCELLED) return(false);
			listener = (PlayerConnectListener)enumeration.nextElement();
			listener.onTrigger(event);
		}
		if(event.cancelStatus == CancelStatus.NOT_CANCELLED){
			return(true);
		}else{
			return(false);
		}
	}
}
