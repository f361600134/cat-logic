package com.cat.server.collection;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public class CollectionTest {

	public static void main(String[] args) {
		// Queue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
		// queue.add(1);
		// queue.add(2);
		// queue.add(3);
		// System.out.println(queue);
		// for (Integer i : queue) {
		// System.out.println(i);
		// }
		//
		Deque<Integer> deque = new ConcurrentLinkedDeque<>();
		for (int i = 1; i <= 10; i++) {
			deque.add(i);
		}
		System.out.println(deque);
		
		Iterator<Integer> iterator =  deque.descendingIterator();
//		while (iterator.hasNext()) {
//			System.out.print(iterator.next());
//		}
		deque.poll();
		deque.add(11);
		System.out.println(deque);
		
//		Iterator<Integer> iterator =  deque.descendingIterator();
		while (iterator.hasNext()) {
			System.out.print(iterator.next());
		}
		
//		for (Integer i : deque) {
//			System.out.println(i);
//		}

//		List<Integer> list = new ConcurrentFixSizeArrayList<Integer>(10);
//		for (int i = 1; i <= 10; i++) {
//			list.add(i);
//		}
//		System.out.println(list);
//		int num = 5;
//		int from = list.size() - num;
//		int end = list.size();
//		List<Integer> ret = new ArrayList<>(list.subList(from, end));
//		System.out.println(ret);
//		Collections.reverse(ret);
//		System.out.println(ret);
//		System.out.println(list);
	}

}
