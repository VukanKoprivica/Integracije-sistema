package util;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RemoteObservable {

	
	private List<ChatListener> remoteObservable;

	public RemoteObservable() {
		this.remoteObservable = new ArrayList<ChatListener>();
	}
	
	public void addListener(ChatListener lisener) {
		this.remoteObservable.add(lisener);
	}
	
	public void removeListener(ChatListener lisener) {
		this.remoteObservable.remove(lisener);
	}
	
	public void notifyObservers(String name,String message) {
		for(ChatListener cl:remoteObservable) {
			try {
				cl.receiveMessage(name, message);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	public void find(String name, String message) {
		for(ChatListener cl:remoteObservable) {
			try {
				if(cl.getByName(name))
					cl.receiveMessage("SERVER",message);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
}
