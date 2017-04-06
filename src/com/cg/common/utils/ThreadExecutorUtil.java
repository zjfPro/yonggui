package com.cg.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * 线程池 
 * 
 * @author 周家雄
 * 
 */
public class ThreadExecutorUtil {
	private static int ThreadNumber = 20;
	private static ExecutorService pool;

	public static void init() {
		pool = Executors.newFixedThreadPool(ThreadNumber);
	}

	public synchronized static ExecutorService getPool() {
		if (pool == null) {
			init();
		}
		return pool;
	}

	public static void setPool(ExecutorService p) {
		pool = p;
	}

	public static void close(ExecutorService pool) throws Exception {
		pool.shutdownNow();
		pool.awaitTermination(0, TimeUnit.NANOSECONDS);
	}

}
