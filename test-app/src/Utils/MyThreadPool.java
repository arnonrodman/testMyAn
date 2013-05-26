package Utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import workers.AddressWorker;
import workers.ImageByAddress;
import workers.ImageByName;

public class MyThreadPool {	
		
	private ArrayBlockingQueue<Runnable> bQueue;
	private ExecutorService es;
	
	public MyThreadPool(ArrayBlockingQueue<Runnable> bQueue, ExecutorService es) {		
		this.bQueue = bQueue;
		this.es = es;
	}

	public void executeGoogleAddress(String latitude,String longitude,String imageToBeSaved,String localUserAlbumFolde){
		es.execute(new AddressWorker(latitude, longitude,imageToBeSaved,localUserAlbumFolde));//longitude
	}
	
	public String executeGoogleImageByAddress(String longitude,String altitude) throws InterruptedException, ExecutionException{
		Future<String> future = es.submit((new ImageByAddress(Long.valueOf(altitude),Long.valueOf(altitude))));
		return future.get();
		
	}
	
	public String executeImageByName(String name) throws InterruptedException, ExecutionException{
		return (es.submit((new ImageByName(name))).get().toString());
	}
	
	public ArrayBlockingQueue<Runnable> getbQueue() {
		return bQueue;
	}

	public void setbQueue(ArrayBlockingQueue<Runnable> bQueue) {
		this.bQueue = bQueue;
	}

	public ExecutorService getEs() {
		return es;
	}

	public void setEs(ExecutorService es) {
		this.es = es;
	}
	
}
