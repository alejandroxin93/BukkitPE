package com.cnkvha.BukkitPE.EventSystem;

public class CancellableEvent extends BaseEvent {
	/*
	 * cancelStatus:
	 * 0 = Not cancelled
	 * 1 = Cancelled
	 * 2 = Force Cancelled
	 */
	public CancelStatus cancelStatus = 0;
	
	public void setCancelled(boolean stat){
		if(this.cancelStatus == CancelStatus.FORCE_CANCELLED) return;
		if(stat){
			this.cancelStatus = CancelStatus.CANCELLED;
		}else{
			this.cancelStatus = CancelStatus.NOT_CANCELLED;
		}
	}
}
