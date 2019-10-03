package com.os.model;

import java.util.Random;

/**
 * Student
 */
public class Student extends Thread {

	private Room room;

	private String name;

	private Random rand;

	private Monitor monitor;

	private boolean verbose;

	public Student(String name, Random rand, Monitor monitor, Room room, boolean verbose) {
		this.name = name;
		this.room = room;
		this.rand = rand;
		this.monitor = monitor;
		this.verbose = verbose;

	}

	@Override
	public void run() {
		try {

			while (true) {
				int decision = rand.nextInt(2);
				int millis = rand.nextInt(100);
				if (decision == 0) {

					if (!monitor.isBusy()) {
						// used to acquire monitor semaphore and set student
						monitor.attend(this);
						if (monitor.isSleep()) {
							monitor.awake();
						}
						millis = rand.nextInt(1000);
						sleep(millis);
						//use to release monitor semaphore and unset student
						monitor.desattend();
					} else {
						if (room.availableSites()) {
							// use the queue into semaphore
							room.enqueue(this);
							if (verbose) {
								System.out.println(name + " fue encolado");
							}
						} else {
							if (verbose) {
								System.out.println(name + " fue a sala de computo");
							}
							millis = rand.nextInt(1000);
							sleep(millis);

						}
					}

				} else {
					if (verbose) {
						System.out.println(name + " Fue a sala de computo");
					}
					millis = rand.nextInt(1000);
					sleep(millis);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String getStudentName() {
		return name;
	}

}
