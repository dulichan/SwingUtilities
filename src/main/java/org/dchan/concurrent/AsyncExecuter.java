package org.dchan.concurrent;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class facilitates the execution of Asynchronous method calls. For
 * example where their is no feedback necessary. An instance of this class is
 * considered as a single Cue. The cue uses a single thread (like a service) to
 * execute tasks). {@link Runnable} instances are used to run the tasks.
 * 
 * @author dulithar
 * 
 */
public class AsyncExecuter {
	/**
	 * Consists of the tasks
	 */
	private Queue<Runnable> taskList = new LinkedList<Runnable>();
	// Switch to stop the service thread
	private boolean stop = false;

	/**
	 * At instantiation the service thread is started.
	 */
	public AsyncExecuter() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				// Will run until stop variable becomes true
				while (!stop) {
					startTaskEngine();
				}
			}
		});
		thread.start();
	}

	private void startTaskEngine() {
		if (!taskList.isEmpty()) {
			Runnable type = (Runnable) taskList.poll();
			type.run();
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Add a task for execution
	 * @param runnable
	 */
	public void addTask(Runnable runnable) {
		taskList.add(runnable);
	}

	/**
	 * Stop the service thread.
	 */
	public void stop() {
		stop = true;
	}

	/**
	 * 
	 * @return true if the service is not active and has tasks that weren't
	 *         executed
	 */
	public boolean isDead() {
		if (!isActive()) {
			if (taskList.size() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return true if active. False if dead.
	 */
	public boolean isActive() {
		return !stop;
	}

	/**
	 * Creates a clone {@link AsyncExecuter} which contains tasks that were not
	 * executed previously
	 */
	public AsyncExecuter clone() {
		AsyncExecuter asyncExecuter = new AsyncExecuter();
		for (Iterator iterator = taskList.iterator(); iterator.hasNext();) {
			Runnable type = (Runnable) iterator.next();
			asyncExecuter.addTask(type);
		}
		return asyncExecuter;
	}
}
