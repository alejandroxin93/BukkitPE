package com.cnkvha.BukkitPE.EventSystem;

import java.util.Vector;

public class BaseManager {
	public static Vector<BaseListener> lv0Handlers = new Vector<BaseListener>();
	public static Vector<BaseListener> lv1Handlers = new Vector<BaseListener>();
	public static Vector<BaseListener> lv2Handlers = new Vector<BaseListener>();
	public static Vector<BaseListener> lv3Handlers = new Vector<BaseListener>();
	public static Vector<BaseListener> lv4Handlers = new Vector<BaseListener>();
	public static Vector<BaseListener> MonitorHandlers = new Vector<>();
	
	public static boolean registerListener(EventPriority priority, BaseListener listener){
		if(!(listener instanceof BaseEvent)){
			return(false);
		}
		switch(priority){
		case LOWEST:
			if(lv0Handlers.contains(listener)) return(false);
			lv0Handlers.add(listener);
			return(true);
		case LOW:
			if(lv1Handlers.contains(listener)) return(false);
			lv1Handlers.add(listener);
			return(true);
		case NORMAL:
			if(lv2Handlers.contains(listener)) return(false);
			lv2Handlers.add(listener);
			return(true);
		case HIGH:
			if(lv3Handlers.contains(listener)) return(false);
			lv3Handlers.add(listener);
			return(true);
		case HIGHEST:
			if(lv4Handlers.contains(listener)) return(false);
			lv4Handlers.add(listener);
			return(true);
		case MONITOR:
			if(MonitorHandlers.contains(listener)) return(false);
			MonitorHandlers.add(listener);
			return(true);
		}
		return(false);
	}
	
	public static void unregisterAllListeners(){
		lv0Handlers = new Vector<BaseListener>();
		lv1Handlers = new Vector<BaseListener>();
		lv2Handlers = new Vector<BaseListener>();
		lv3Handlers = new Vector<BaseListener>();
		lv4Handlers = new Vector<BaseListener>();
		MonitorHandlers = new Vector<>();
	}
	
	
	/* WE HAVE TO IMPLEMENT THIS IN EACH EVENTMANAGER */
	
	public static boolean callEvent() {
		return(true);
	}
	
}
