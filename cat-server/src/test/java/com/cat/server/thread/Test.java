package com.cat.server.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	
	public static void main(String[] args) {
		Domain domain = new Domain();
		
		Calculater cal1 = new Calculater();
		Calculater cal2 = new Calculater();
		
		Context context1 = new Context(domain, cal1);
		Context context2 = new Context(domain, cal2);
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		executor.submit(context1);
		executor.submit(context2);
		
		executor.shutdown();
		System.out.println("================");
		
		while (true) {
			if (executor.isTerminated()) {
				System.out.println("=============cal1:"+cal1);
				System.out.println("=============cal2:"+cal2);
				break;
			}
		}
		
	}

}
