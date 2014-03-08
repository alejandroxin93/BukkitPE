package com.cnkvha.BukkitPE.EventSystem;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.cnkvha.BukkitPE.Debugging.Log;

public class EventSystem {
	public static HashMap<String, HandlerList> handlers = new HashMap<String, HandlerList>();
	
	public static boolean registerEvent(String handlerName, EventPriority priority, Object instance, String methodName){
		if(checkRegisteredEvent(handlerName, instance, methodName)){
			Log.Error("Failed to register event [" + handlerName + "] on [" + instance.getClass().getName() + "." + methodName + "] due to handler already exists! ");
			return(false);
		}
		//HandlerElement handler = new HandlerElement(handlerName, priority, instance, methodName);
		if(!(handlers.containsKey(handlerName))) handlers.put(handlerName, new HandlerList(handlerName));
		handlers.get(handlerName).registerHandler(priority, instance, methodName);
		return(true);
	}
	
	public static boolean checkRegisteredEvent(String handlerName, Object instance, String methodName){
		if(!(handlers.containsKey(handlerName))) return(false);
		return(handlers.get(handlerName).checkExists(instance, methodName));
	}
	
	public static boolean callHandler(String eventName, HashMap<String, Object> data){
		if(eventName == null) return(false);
		if(data == null) data = new HashMap<String, Object>();
		if(!handlers.containsKey(eventName)) return(true); //If no handlers registered, return true directly
		
		return(true);
	}
}
