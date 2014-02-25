package com.cnkvha.BukkitPE;

import java.net.DatagramPacket;

import com.cnkvha.BukkitPE.Debugging.Log;
import com.cnkvha.BukkitPE.Network.EventSystem.UDPRecvEvent;
import com.cnkvha.BukkitPE.Network.EventSystem.UDPRecvEventListener;

public class RecvListener implements UDPRecvEventListener {
	@Override
	public void onRecv(UDPRecvEvent event) {
		// TODO Auto-generated method stub
		DatagramPacket packet = event.getPacket();
		Log.Debug("Got " + packet.getLength() + "bytes from " + packet.getAddress().toString());
	}
}
