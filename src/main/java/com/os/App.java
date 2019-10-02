  package com.os;

import java.util.Random;
import java.util.concurrent.Semaphore;
import com.os.model.Monitor;
import com.os.model.Student;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//true for using FIFO
        Semaphore waitRoom = new Semaphore(0, true);
        
        //A binary semaphore
        Semaphore monitorSemaphore = new Semaphore(1);
        
        
        Random rand = new Random();

        Monitor monitor = new Monitor(waitRoom);
        Student student1 = new Student("pepe", rand, monitor, waitRoom, monitorSemaphore);
        Student student2 = new Student("jezuh", rand, monitor, waitRoom, monitorSemaphore);

        monitor.start();
        student1.start();
        student2.start();
    }
}
