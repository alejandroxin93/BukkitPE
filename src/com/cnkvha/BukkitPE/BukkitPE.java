package com.cnkvha.BukkitPE;

import java.util.Scanner;

import com.cnkvha.BukkitPE.Debugging.*;
import com.cnkvha.BukkitPE.Utils.*;

public class BukkitPE {
	
	public static boolean isServerStopping = false;
	
	public static void main(String[] args) throws Exception{
		Definations.config = new Config("server.properties", Constants.SERVERPROPERTIES_PRESET);
		Definations.ServerIP = (String)Definations.config.get("server-ip");
		Definations.ServerPort = Integer.parseInt(Definations.config.get("server-port").toString());
		Log.Info("Starting Minecraft PE server on UDP port " + Definations.config.get("server-port"));
		Definations.mainThread = new MainThread();
		Definations.mainThread.start();
		Log.Debug("Entering console loop...");
		Scanner scanner = new Scanner(System.in);
		String input = "";
		while(isServerStopping == false){
			input = scanner.nextLine();
			Log.Info("Running command: " + input);
		}
		//TODO: Add command event
		scanner.close();
	}
	
	
	public static void runCommand(String fullCommand){
		String[] cmd = fullCommand.split(" ");
		switch(cmd[0]){
		case "stop":
			isServerStopping = true;
		}
	}
	
}
