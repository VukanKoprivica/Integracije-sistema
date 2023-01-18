package util;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.RUcesnik;

public interface ChatListener extends Remote  {
	public void receiveMessage(String name, String message) throws RemoteException;
	public boolean get(String name) throws RemoteException ;
	
}
