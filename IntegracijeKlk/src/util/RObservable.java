package util;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import client.RUcesnik;

public class RObservable {

	private List<ChatListener> remoteObservers;
	private RUcesnik Ucesnik;

	
	public RObservable() {
		this.remoteObservers = new ArrayList<ChatListener>();
	}
	
	public void addRemoteObservers(ChatListener observer) {
		this.remoteObservers.add(observer);
	}
	
	public void deleteRemoteObservers(ChatListener observer) {
	
		
		this.remoteObservers.remove(observer);
	}
	
	public void find(String name,String message) throws RemoteException {
		for(ChatListener iro:remoteObservers) {
			 if(iro.get(name)) {
				iro.receiveMessage("SERVER", message);
			}
			
		}
		
	}
	
	public void deleteSve() {
		this.remoteObservers.clear();
	}
	
	public void notifyRemoteObservers( String name,String message) {
		for(ChatListener iro:remoteObservers) {
			try {
				iro.receiveMessage(name, message);
			} catch (Exception e) {
				System.err.println("notify :" +e);
				e.printStackTrace();
			}
			
		}
	}
	
	
}
