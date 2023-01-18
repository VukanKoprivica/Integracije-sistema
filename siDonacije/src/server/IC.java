package server;


import java.rmi.RemoteException;

import util.IRObservable;

public interface IC extends IRObservable {
	void collectDonation(int d) throws RemoteException;
}
