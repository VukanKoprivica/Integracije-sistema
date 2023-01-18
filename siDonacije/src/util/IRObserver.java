package util;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRObserver  extends Remote{
	public void update (Object arg) throws RemoteException;
}
