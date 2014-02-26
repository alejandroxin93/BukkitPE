package com.cnkvha.BukkitPE.EventSystem.Interfaces;

import com.cnkvha.BukkitPE.EventSystem.*;
import com.cnkvha.BukkitPE.EventSystem.Events.PlayerConnectEvent;

public interface PlayerConnectListener extends BaseListener {
	public void onTrigger(PlayerConnectEvent event);
}
