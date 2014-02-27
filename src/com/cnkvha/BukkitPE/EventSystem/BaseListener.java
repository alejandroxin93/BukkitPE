package com.cnkvha.BukkitPE.EventSystem;

import java.util.EventListener;

public abstract interface BaseListener extends EventListener {
	public void onTrigger(BaseEvent event);
}
