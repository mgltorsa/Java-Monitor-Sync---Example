package com.os.model;

import java.util.concurrent.Semaphore;

/**
 * Room
 */
public class Room {

	private Semaphore waitSemaphore;

	private int sites;

	private boolean verbose;

	public Room(int limitSites, Semaphore waitSemaphore, boolean verbose) {
		this.waitSemaphore = waitSemaphore;
		this.sites = limitSites;
		this.verbose = verbose;
	}

	public boolean areEnqueued() {
		return waitSemaphore.hasQueuedThreads();
	}

	public boolean availableSites() {
		return sites > 0;
	}

	public void enqueue(Student student) {
		try {
			if (verbose) {
				System.out.println(student.getStudentName() + " Esta esperando");
			}
			sites -= 1;
			//used to set current thread to wait until next release
			waitSemaphore.acquire();
			//used to set permits to 0 for wait the next time
			waitSemaphore.acquire();
			sites += 1;
			if (verbose) {
				System.out.println(" Hay un nuevo lugar en la fila");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}