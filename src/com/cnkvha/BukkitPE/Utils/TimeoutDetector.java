package com.cnkvha.BukkitPE.Utils;

import java.util.ArrayList;
import java.util.Date;

import com.cnkvha.BukkitPE.APIs.*;
import com.cnkvha.BukkitPE.APIManager;
import com.cnkvha.BukkitPE.Player;

public class TimeoutDetector extends Thread {
	
	private boolean isStopping = false; 
	
	@Override
	public void run() {
		ArrayList<String> toDisconnect = new ArrayList<String>();
		while(isStopping == false){
			//Detect clients one by one
			toDisconnect.clear();
			for(Player p : ((PlayerAPI)APIManager.get("player")).clients.values()){
				if(new Date().getTime() - p.lastRecv > 30000){
					toDisconnect.add(p.ckey);
				}
			}
			for(Object ckey : toDisconnect.toArray()){
				((PlayerAPI)APIManager.get("player")).clients.get(ckey).disconnect("timed out");
			}
		}
	}
}
