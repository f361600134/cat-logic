package com.cat.server.thread;

public class Context implements Runnable {
	
	private Domain domain;
	private Calculater cal;
	
	public Context(Domain domain, Calculater cal) {
		this.domain = domain;
		this.cal = cal;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			domain.calculate(cal);
			System.out.println("ThreaName:"+Thread.currentThread().getName()+", domain:"+domain+", cal:"+cal);
		}
	}
	
	

}
