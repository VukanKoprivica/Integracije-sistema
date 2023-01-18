package util;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class RObserver extends UnicastRemoteObject implements ChatListener {

	public RObserver() throws RemoteException{
		super();
	}
}
