package com.cnkvha.BukkitPE.EventSystem;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.cnkvha.BukkitPE.Debugging.Log;

public class HandlerList {
	private String eventName;
	public HashMap<EventPriority, ArrayList<HandlerElement>> list = new HashMap<EventPriority, ArrayList<HandlerElement>>();
	
	public HandlerList(String eventName){
		this.eventName = eventName;
	}
	
	public String getEventName(){
		return(this.eventName);
	}
	
	public boolean registerHandler(EventPriority priority, Object instance, String methodName){
		//Check method exist
		@SuppressWarnings("unused")
		Method method;
		try {
			method = instance.getClass().getMethod(methodName, HashMap.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// Failed to get the method
			e.printStackTrace();
			Log.Error("Failed to register event [" + this.eventName + "] on [" + instance.getClass().getName() + "." + methodName + "] due to method not available! ");
			return(false);
		}
		HandlerElement handler = new HandlerElement(this.eventName, priority, instance, methodName);
		if(!(this.list.containsKey(priority))) this.list.put(priority, new ArrayList<HandlerElement>());
		this.list.get(priority).add(handler);
		return(true);
	}
	
	public boolean checkExists(Object instance, String methodName){
		Iterator<HandlerElement> iterator;
		HandlerElement handler;
		for(ArrayList<HandlerElement> handlers : list.values()){
			iterator = handlers.iterator();
			while(iterator.hasNext()){
				handler = iterator.next();
				if(handler.instance == instance && handler.methodName == methodName){
					return(true);
				}
			}
		}
		return(false);
	}
	
	public boolean callEvent(HashMap<String, Object> data){
		Iterator<HandlerElement> iterator;
		int status = CancelStatus.NOT_CANCELLED;
		if(list.containsKey(EventPriority.MONITOR)){
			iterator = list.get(EventPriority.MONITOR).iterator();
			while(iterator.hasNext()){
				iterator.next().invoke(data);
			}
		}
		if(list.containsKey(EventPriority.HIGHEST)){
			iterator = list.get(EventPriority.HIGHEST).iterator();
			while(iterator.hasNext()){
				status = iterator.next().invoke(data);
				if(status == CancelStatus.FORCE_CANCELLED) return(false);
			}
		}
		if(list.containsKey(EventPriority.HIGH)){
			iterator = list.get(EventPriority.HIGH).iterator();
			while(iterator.hasNext()){
				status = iterator.next().invoke(data);
				if(status == CancelStatus.FORCE_CANCELLED) return(false);
			}
		}
		if(list.containsKey(EventPriority.NORMAL)){
			iterator = list.get(EventPriority.NORMAL).iterator();
			while(iterator.hasNext()){
				status = iterator.next().invoke(data);
				if(status == CancelStatus.FORCE_CANCELLED) return(false);
			}
		}
		if(list.containsKey(EventPriority.LOW)){
			iterator = list.get(EventPriority.LOW).iterator();
			while(iterator.hasNext()){
				status = iterator.next().invoke(data);
				if(status == CancelStatus.FORCE_CANCELLED) return(false);
			}
		}
		if(list.containsKey(EventPriority.LOWEST)){
			iterator = list.get(EventPriority.LOWEST).iterator();
			while(iterator.hasNext()){
				status = iterator.next().invoke(data);
				if(status == CancelStatus.FORCE_CANCELLED) return(false);
			}
		}
		return(status == CancelStatus.CANCELLED ? false : true);
	}
	
}
