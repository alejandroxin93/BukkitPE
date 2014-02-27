package com.cnkvha.BukkitPE.EventSystem;

import com.cnkvha.BukkitPE.EventSystem.DefaultListeners.DefaultPlayerConnectEvent;
import com.cnkvha.BukkitPE.EventSystem.Managers.PlayerConnectEventManager;

public class DefaultEvents {
	public static void registerAllDefaultEvents(){
		PlayerConnectEventManager.registerListener(EventPriority.LOWEST, new DefaultPlayerConnectEvent());
	}
}
