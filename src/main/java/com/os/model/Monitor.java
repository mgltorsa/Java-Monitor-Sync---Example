package com.os.model;

import java.util.concurrent.Semaphore;

/**
 * Monitor
 */
public class Monitor extends Thread {

	private Student currentStudent;

	private Semaphore sleepSemaphore;

	private Semaphore monitorSemaphore;

	private Semaphore waitRoom;

	private boolean verbose;

	public Monitor(Semaphore waitRoom, Semaphore monitorSemaphore, boolean verbose) {
		this.waitRoom = waitRoom;
		this.sleepSemaphore = new Semaphore(1);
		this.monitorSemaphore = monitorSemaphore;
		this.verbose = verbose;
	}

	public boolean isBusy() {
		return monitorSemaphore.availablePermits() == 0;
	}

	public void attend(Student student) {
		try {
			monitorSemaphore.acquire();
			this.currentStudent = student;
			if (verbose) {
				System.out.println("Entra estudiante ->" + student.getStudentName());
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void desattend() {
		if (verbose) {
			System.out.println("Monitor termina de atender a -> " + currentStudent.getStudentName());
		}
		this.currentStudent = null;
		if (verbose) {
			System.out.println("Hay estudiantes en la cola");
		}
		waitRoom.release();
		if (verbose) {
			System.out.println(waitRoom.availablePermits());
			System.out.println("El monitor hizo pasar a un estudiante de la cola");
		}
		monitorSemaphore.release();

	}

	public void toSleep() {
		try {
			// Set sem in red
			sleepSemaphore.acquire();

			if (verbose) {
				System.out.println("Monitor va a dormir");
			}

			// wait release
			sleepSemaphore.acquire();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isSleep() {
		return sleepSemaphore.availablePermits() == 0;
	}

	public void awake() {
		sleepSemaphore.release();
		if (verbose) {
			System.out.println("El monitor fue despertado");
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				while (currentStudent == null) {
					toSleep();
				}

				while (currentStudent != null) {
					if (verbose) {
						System.out.println("Monitor esta atendiendo a " + currentStudent.getStudentName());
					}
					sleep(100);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
