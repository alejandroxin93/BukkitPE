package com.cnkvha.BukkitPE.EventSystem;

import java.util.EventObject;

public class BaseEvent extends EventObject {
	public String name;
	
	public BaseEvent(){
		super(false);
	}
	
	public BaseEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	
	public String getEventName(){
		String n = this.name;
		if(n == null){
			n = getClass().getSimpleName();
		}
		return(n);
	}
}
