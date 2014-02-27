package com.cnkvha.BukkitPE.Utils;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

import com.cnkvha.BukkitPE.MainThread;
import com.cnkvha.BukkitPE.Player;
import com.cnkvha.BukkitPE.Network.UDPListener;

public class Definations {
	public static Config config;
	
	public static String ServerIP = "0.0.0.0";
	public static int ServerPort = 19132;
	
	public static UDPListener socket;
	
	public static MainThread mainThread;
	public static TimeoutDetector timeoutDetector;
	
	public static HashMap<String, Player> clients = new HashMap<String, Player>();
	
}
