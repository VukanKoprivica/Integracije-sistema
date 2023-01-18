package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.Ucesnik;
import util.ChatListener;
import xml.XMLDOM;

public interface ChatServer extends Remote {

	public void sendMessage(String name, String message) throws RemoteException;
	
	public void addListener(ChatListener listener) throws RemoteException;
	
	public void removeListener(ChatListener listener) throws RemoteException;


	
	
}
