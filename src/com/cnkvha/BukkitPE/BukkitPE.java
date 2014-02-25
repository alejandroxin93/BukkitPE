package com.cnkvha.BukkitPE;

import java.util.Scanner;

import org.yaml.snakeyaml.Yaml;

import com.cnkvha.BukkitPE.Debugging.*;
import com.cnkvha.BukkitPE.Network.UDPListener;
import com.cnkvha.BukkitPE.Utils.*;

public class BukkitPE {
	public static void main(String[] args) throws Exception{
		Definations.config = new Config("server.properties", Constants.SERVERPROPERTIES_PRESET);
		Definations.ServerIP = (String)Definations.config.get("server-ip");
		Definations.ServerPort = Integer.parseInt(Definations.config.get("server-port").toString());
		Log.Info("Starting Minecraft PE server on UDP port " + Definations.config.get("server-port"));
		Definations.socket = new UDPListener(Definations.ServerIP, Definations.ServerPort);
		Definations.socket.registerRecvEvent(new RecvListener());
		Definations.mainThread = new MainThread();
		Definations.mainThread.start();
		Log.Debug("Entering console loop...");
		Scanner scanner = new Scanner(System.in);
		String input = "";
		while(true){
			input = scanner.nextLine();
			Log.Info("Running command: " + input);
		}
	}
}
