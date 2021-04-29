package com.cat.robot.common.akka;

import java.lang.reflect.Method;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSystemImpl;
import akka.actor.Cancellable;
import akka.actor.TypedActor;
import akka.actor.TypedActor.MethodCall;
import akka.actor.TypedProps;
import akka.util.Timeout;

final public class AkkaContext {

	private static final Logger logger = LoggerFactory.getLogger(AkkaContext.class.getName());
	
	public final static String SYSTEM_NAME = "system"; //根节点

	private final static ActorSystem SYSTEM = ActorSystem.create(SYSTEM_NAME);

	public static ActorSystem system() {
		return SYSTEM;
	}

	/**
	 * 打印jvm中所有的actor
	 *
	 * 用于检查actor泄露
	 */
	public static void printActors() {
		try {
			Method m = ActorSystemImpl.class.getDeclaredMethod("printTree");
			logger.info((String) m.invoke(SYSTEM));
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 停止一个TypedActor
	 * 
	 * @param o
	 */
	public static void stopTypedActor(Object o) {
		TypedActor.get(SYSTEM).stop(o);
	}

	/**
	 * 创建一个TypedActor
	 * 
	 * @param interfaceCls
	 * @param implementationCls
	 * @param <T>
	 * @return
	 */
	public static <T> T createTypedActor(Class<T> interfaceCls, Class implementationCls) {
		Timeout timeout = Timeout.create(Duration.ofMillis(10000));
		return TypedActor.get(SYSTEM).typedActorOf(new TypedProps<T>(interfaceCls, implementationCls).withTimeout(timeout));
	}

	/**
	 * 获得一个TypedActor的ActorRef
	 *
	 * @param typedActor
	 * @return
	 */
	public static ActorRef getTypedActorRef(Object typedActor) {
		return TypedActor.get(SYSTEM).getActorRefFor(typedActor);
	}
	
	/**
	 * akka定时器
	 * 
	 * @param duration 间隔时间, 毫秒
	 * @param task	      执行任务
	 * @return {@link Cancellable}  不需要时把定时器关闭
	 */
	public static Cancellable schedule(long duration, Runnable task) {
		Cancellable c = SYSTEM.scheduler().schedule(Duration.ZERO, 
				Duration.ofMillis(duration),
				task, SYSTEM.dispatcher());
		return c;
	}
	
	/**
	 * TypedActor定时器 
	 * 
	 * @param duration    间隔时间, 毫秒
	 * @param typedActor  typedActor的ActorRef
	 * @param methodCall  TypedActor方法调用
	 * @return {@link Cancellable}  不需要时把定时器关闭
	 */
	public static Cancellable schedule(long duration, ActorRef typedActor, MethodCall methodCall) {
		Cancellable c = SYSTEM.scheduler().schedule(Duration.ZERO,
				Duration.ofMillis(duration), 
				typedActor, methodCall, SYSTEM.dispatcher(), null);
		return c;
	}
	
}
