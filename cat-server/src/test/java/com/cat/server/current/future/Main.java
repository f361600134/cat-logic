package com.cat.server.current.future;

import java.util.concurrent.Callable;

import com.cat.api.core.task.impl.ListenableFutureExecutor;
import com.google.common.util.concurrent.FutureCallback;

public class Main {
	
	public static void main(String[] args) {
		ListenableFutureExecutor executor = new ListenableFutureExecutor("default");
		executor.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName()+" find somethingm look at it!");
				return 1000;
			}
		}, new FutureCallback<Integer>() {
			@Override
			public void onSuccess(Integer result) {
				System.out.println(Thread.currentThread().getName()+" Wow, it's "+result+" dollars");
			}

			@Override
			public void onFailure(Throwable t) {
				System.out.println("What the hall for you!");
			}
		});
		
	}

}
