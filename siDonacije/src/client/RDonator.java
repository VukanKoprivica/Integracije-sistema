package client;

import java.rmi.RemoteException;

import server.IC;
import util.RObserver;

public class RDonator extends RObserver {
	private IC colector;
	private Donator don;

	public RDonator(IC colector ,Donator donor ) throws RemoteException {
		super();
		this.colector = colector;
		this.don = donor;
		this.colector.addRObserver(this);
		
	}
	
	public void terminate() throws RemoteException{
		this.colector.deleteRObserver(this);
		
	}

	@Override
	public void update(Object arg){
		
		if(arg instanceof Integer)
			this.don.notifyColected((Integer) arg);
		
	}

}
