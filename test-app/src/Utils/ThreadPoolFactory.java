package Utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {
	private static final int MAX_THREADS = 10;
	private static final int MAX_WORKERS = 100;
	
	private ArrayBlockingQueue<Runnable> bQueue;
	private MyThreadPool myThreadPool;
	public MyThreadPool craeteMyThreadPool(){
		
		if(bQueue == null && myThreadPool == null){
			 bQueue = new ArrayBlockingQueue<Runnable>(MAX_WORKERS, true);
			 myThreadPool = new MyThreadPool(
					 bQueue,new ThreadPoolExecutor(MAX_THREADS,MAX_THREADS,1,TimeUnit.MINUTES,bQueue,new ThreadPoolExecutor.CallerRunsPolicy()));
		}
		
		return myThreadPool;
	}
}
