package util;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRObservable extends Remote{
	
	public void addRObserver (IRObserver ro) throws RemoteException;

	public void deleteRObserver (IRObserver ro) throws RemoteException;
}
