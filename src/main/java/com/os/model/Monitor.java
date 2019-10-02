package com.os.model;

import java.util.concurrent.Semaphore;

/**
 * Monitor
 */
public class Monitor extends Thread {

	private Student currentStudent;

	private Semaphore sleepSemaphore;

	private Semaphore waitRoom;

	public Monitor(Semaphore waitRoom) {
		this.waitRoom = waitRoom;
		this.sleepSemaphore = new Semaphore(0);
	}

	public void attend(Student student) {
		this.currentStudent = student;
	}

	public void desattend() {
		this.currentStudent = null;
		System.out.println("Monitor terminated to attend the student - " + currentStudent.getName());
	}
	
	public void toSleep() {
		try {
			sleepSemaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isSleep() {
		return sleepSemaphore.availablePermits()==0;
	}
	
	
	public void awake() {
		sleepSemaphore.release();
		try {
			sleepSemaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				while (currentStudent == null) {
					System.out.println("Monitor is sleeping");
					toSleep();
				}

				while (currentStudent != null) {
					System.out.println("Monitor is attending the student - " + currentStudent.getName());
				}

				if (waitRoom.hasQueuedThreads()) {
					waitRoom.release();
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
