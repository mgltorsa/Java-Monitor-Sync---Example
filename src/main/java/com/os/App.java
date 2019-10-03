  package com.os;

import java.util.Random;
import java.util.concurrent.Semaphore;
import com.os.model.Monitor;
import com.os.model.Room;
import com.os.model.Student;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	int n = 3;
//    	if(args[0]!=null) {
//    		try {
//				
//    			n= Integer.parseInt(args[0]);
//    			throw new Exception("gg");
//    		} catch (Exception e) {
//				System.out.println("error, print a natural number");
//			}
//    		System.out.println("setted default n as 3");
//    		n=3;
//    	}
    	String name = "Pepe";
    	//true for using FIFO
        Semaphore waitRoom = new Semaphore(0, true);
        
        //A binary semaphore
        Semaphore monitorSemaphore = new Semaphore(1);

        Random rand = new Random();

        //verbose = true for print states
        Room room = new Room(3, waitRoom, true);
        
        //verbose = true for print states
        Monitor monitor = new Monitor(waitRoom, monitorSemaphore, true);
        monitor.start();
        
        
        for (int i = 0; i < n; i++) {
        	//verbose = true for print states
			Student student	= new Student(name+""+i, rand, monitor, room, true);
			student.start();
		}
        
        
    }
}
