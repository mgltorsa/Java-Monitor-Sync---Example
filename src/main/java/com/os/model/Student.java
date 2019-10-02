package com.os.model;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Student
 */
public class Student extends Thread {


    private Semaphore waitRoomSemaphore;

    private Semaphore monitorSemaphore;

    private String name;

    private Random rand;

    private Monitor monitor;


    public Student(String name, Random rand, Monitor monitor ,Semaphore waitRoom, Semaphore monitorSempahore) {
        this.name = name;
        this.waitRoomSemaphore = waitRoom;
        this.monitorSemaphore = monitorSempahore;

    }

    @Override
    public void run() {
        try {
            int decision = rand.nextInt(2);
            switch (decision) {
                // Monitor
                case 0:
                    if(monitorSemaphore.availablePermits()>0){
                    	monitorSemaphore.acquire();
                        int time = rand.nextInt(1000);
                        monitor.attend(this);
                        sleep(time);
                        monitor.desattend();
                        monitorSemaphore.release();
                    }
                    else if (monitorSemaphore.availablePermits() == 0) {
                    	waitRoomSemaphore.acquire();

                    } else {
                        int sleepTime = rand.nextInt(1000);
                        sleep(sleepTime);
                    }
                    break;

                // Sala de computo
                case 1:
                    int sleepTime = rand.nextInt(1000);
                    sleep(sleepTime);
                    break;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    
    public String getStudentName() {
        return name;
    }


}
