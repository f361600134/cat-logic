package com.cat.server.thread;

public class Calculater {
	
	//number自增, count递减
	private int number;
	private int count;
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Calculater [number=" + number + ", count=" + count + "]";
	}
	
	
}
