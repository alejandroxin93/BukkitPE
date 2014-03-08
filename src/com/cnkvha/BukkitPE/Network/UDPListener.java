package com.cnkvha.BukkitPE.Network;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Vector;

import com.cnkvha.BukkitPE.Debugging.Log;
import com.cnkvha.BukkitPE.Network.EventSystem.UDPRecvEvent;
import com.cnkvha.BukkitPE.Network.EventSystem.UDPRecvEventListener;
import com.cnkvha.BukkitPE.Utils.Helper;

public class UDPListener extends Thread {
	private SocketAddress localAddr;
	private DatagramSocket socket;
	
	private InetAddress LocalAddr;
	private String IPAddr = "0.0.0.0";
	private int Port = 0;
	
	private Vector repo = new Vector();
	
	private boolean isStopping = false;
	
	public UDPListener(String IP, int port) throws UnknownHostException
	{
		this.IPAddr = IP;
		this.LocalAddr = InetAddress.getByName(IP);
		this.Port = port;
		try {
			this.socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.Error("Socket creation faild on " + IP + ":" + Port);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void registerRecvEvent(UDPRecvEventListener l){
		this.repo.addElement(l);
	}
	
	public void sendTo(byte[] buff, String ip, int port) throws Exception{
		Log.Debug("Sending: \n" + Helper.toHex(buff));
		DatagramPacket p = new DatagramPacket(buff, buff.length, InetAddress.getByName(ip), port);
		this.socket.send(p);
	}
	public void sendTo(byte[] buff, SocketAddress addr) throws Exception{
		Log.Debug("Sending: \n" + Helper.toHex(buff));
		DatagramPacket p = new DatagramPacket(buff, buff.length, addr);
		this.socket.send(p);
	}
	
	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		Log.Debug("Socket " + this.IPAddr + ":" + this.Port + " started! ");
		byte[] buff;
		DatagramPacket pk;
		Enumeration enumeration;
		UDPRecvEventListener listener;
		while(this.isStopping == false){
			buff = new byte[65535];
			pk = new DatagramPacket(buff, buff.length);
			try {
				this.socket.receive(pk);
				Log.Debug("Socket got data from " + pk.getAddress().toString() + ":" + pk.getPort() + "! ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			} 
			enumeration = this.repo.elements();
			while(enumeration.hasMoreElements()){
				listener = (UDPRecvEventListener)enumeration.nextElement();
				listener.onRecv(new UDPRecvEvent(pk));
			}
		}
	}
}
