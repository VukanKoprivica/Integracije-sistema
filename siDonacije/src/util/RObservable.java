package util;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RObservable {
	
	private List<IRObserver> remoteObservers;

	/**
	 * Creates a new RemoteObservable object
	 */
	public RObservable() {
		this.remoteObservers = new ArrayList<IRObserver>();
	}

	/**
	 * Adds a new observer to this, the Observable object
	 * 
	 * @param observer
	 *            IRemoteObserver to add
	 */
	public void addRemoteObserver(IRObserver observer) {
		this.remoteObservers.add(observer);
	}

	/**
	 * Removes an observer from this, the observable object.
	 * 
	 * @param observer
	 *            IRemoteObserver to be removed
	 */
	public void deleteRemoteObserver(IRObserver observer) {
		this.remoteObservers.remove(observer);
	}

	/**
	 * Removes all the observers from this Observable object.
	 */
	public void deleteRemoteObservers() {
		this.remoteObservers.clear();
	}

	/**
	 * Returns the number of subscribed observers.
	 */
	public int countRemoteObservers() {
		return this.remoteObservers.size();
	}

	/**
	 * Notifies to observers if this Observable object has been changed
	 * 
	 * @param arg
	 *            Object with parameters to be send to the observers
	 */
	public void notifyRemoteObservers(Object arg) {

		for (IRObserver iro : remoteObservers) {
			try {
				iro.update(arg);
			} catch (RemoteException re) {
				System.err
						.println("observerRMI.util.RemoteObservable.notifyRemoteObservers(): "
								+ re);
				re.printStackTrace();
			}

		}

	}

}
