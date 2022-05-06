package com.cat.server.thread;

public class Domain {
	
	public void calculate(Calculater calculater) {
		try {
			Thread.sleep(1);
			calculater.setNumber(calculater.getNumber() + 1);
			this.afterCalculate(calculater);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void afterCalculate(Calculater calculater) {
		try {
			Thread.sleep(1);
			calculater.setCount(calculater.getCount() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
