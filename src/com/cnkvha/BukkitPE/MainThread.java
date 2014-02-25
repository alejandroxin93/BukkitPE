package com.cnkvha.BukkitPE;

import com.cnkvha.BukkitPE.Debugging.Log;
import com.cnkvha.BukkitPE.Utils.Definations;

public class MainThread extends Thread {
	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
		Log.Info("Main thread starting...");
		Definations.socket.start();
	}
}
