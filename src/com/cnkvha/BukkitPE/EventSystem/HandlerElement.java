package com.cnkvha.BukkitPE.EventSystem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.cnkvha.BukkitPE.Debugging.Log;

public class HandlerElement {
	public EventPriority priority = EventPriority.LOWEST;
	public Object instance;
	public String methodName;
	private String eventName;
	private Method method;
	
	public HandlerElement(String eventName, EventPriority priority, Object instance, String methodName){
		this.eventName = eventName;
		this.priority = priority;
		this.instance = instance;
		this.methodName = methodName;
		try {
			this.method = this.instance.getClass().getMethod(this.methodName, HashMap.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.Error("Faild to invoke event: [" + this.eventName + "] due to can not find the method! ");
		}
	}
	
	public int invoke(HashMap<String, Object> arg){
		if(!(method instanceof Method)){
			Log.Error("Faild to invoke event: [" + this.eventName + "] due to method is not available! ");
			return(CancelStatus.NOT_CANCELLED);
		}
		int ret = CancelStatus.NOT_CANCELLED;
		try {
			Object retObj;
			retObj = method.invoke(this.instance, arg);
			ret = retObj == null ? CancelStatus.NOT_CANCELLED : (int)retObj;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			Log.Error("Faild to invoke event: [" + this.eventName + "] due to failed to call the method! ");
			return(CancelStatus.NOT_CANCELLED);
		}
		return(ret);
	}
}
