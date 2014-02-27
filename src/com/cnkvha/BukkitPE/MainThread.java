package com.cnkvha.BukkitPE;

import java.net.UnknownHostException;

import com.cnkvha.BukkitPE.Debugging.Log;
import com.cnkvha.BukkitPE.EventSystem.DefaultEvents;
import com.cnkvha.BukkitPE.Network.UDPListener;
import com.cnkvha.BukkitPE.Utils.Definations;
import com.cnkvha.BukkitPE.Utils.TimeoutDetector;

public class MainThread extends Thread{
	@Override
	public synchronized void start(){
		try {
			Definations.socket = new UDPListener(Definations.ServerIP, Definations.ServerPort);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		Log.Debug("Starting up timeout detector...");
		Definations.timeoutDetector = new TimeoutDetector();
		Definations.timeoutDetector.start();
		Log.Info("Registering default event listeners...");
		DefaultEvents.registerAllDefaultEvents();
		Definations.socket.registerRecvEvent(new RecvListener());
		Definations.socket.start();
	}
}
