package client;

import java.rmi.RemoteException;

import server.ChatServer;
import util.RObserver;

public class RUcesnik extends RObserver  {
	private ChatServer chatRoom;
	private Ucesnik ucesnik;

	public RUcesnik(ChatServer chatRoom,Ucesnik ucesnik) throws RemoteException, InterruptedException {
		super();
		this.chatRoom=chatRoom;
		this.ucesnik=ucesnik;
		
		this.chatRoom.addListener(this);
	}
	@Override
	public boolean get(String name) throws RemoteException {
		if(ucesnik.getName().equals(name)) {
			return(true);
		}
	 return false;
		
	}

	@Override
	public void receiveMessage(String name, String message) throws RemoteException {
		this.ucesnik.primljena(name, message);
		
	}

}
