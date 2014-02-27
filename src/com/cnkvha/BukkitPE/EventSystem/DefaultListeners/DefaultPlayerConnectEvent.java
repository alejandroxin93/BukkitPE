package com.cnkvha.BukkitPE.EventSystem.DefaultListeners;

import com.cnkvha.BukkitPE.EventSystem.BaseEvent;
import com.cnkvha.BukkitPE.EventSystem.Events.PlayerConnectEvent;
import com.cnkvha.BukkitPE.EventSystem.Interfaces.PlayerConnectListener;

public class DefaultPlayerConnectEvent implements PlayerConnectListener {
	@Override
	public void onTrigger(BaseEvent event) {
		// TODO Auto-generated method stub
		PlayerConnectEvent e = (PlayerConnectEvent)event;
		e.setForceCancel();
	}
}
