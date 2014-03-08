package com.cnkvha.BukkitPE;

import java.util.HashMap;

import com.cnkvha.BukkitPE.APIs.*;
import com.cnkvha.BukkitPE.Debugging.Log;

public class APIManager {
	public static HashMap<String, BaseAPI> apis = new HashMap<String, BaseAPI>();
	
	public static BaseAPI get(String name){
		if(apis.containsKey(name)){
			return(apis.get(name));
		}else{
			return(null);
		}
	}
	
	public static void loadAllAPIs(){
		loadAPI(new PlayerAPI());
		loadAPI(new ChatAPI());
	}
	
	public static void loadAPI(BaseAPI api){
		if(api == null){
			Log.Error("An API faild to load! ");
			return;
		}
		Log.Debug("Loading API: " + api.getName() + " ...");
		api.init();
		apis.put(api.getName(), api);
	}
}
