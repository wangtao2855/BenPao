package huxibianjie.com.gonggong.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class ThreadManager {
    private static ExecutorService mCacheThreadExecutor = null;
    public static ThreadManager threadManager= new ThreadManager();
    private ThreadManager(){
        mCacheThreadExecutor = Executors.newCachedThreadPool();// 一个没有限制最大线程数的线程池
    }
    public static ThreadManager getInstance(){
        return threadManager;
    }
    public void ExecutorServiceThread(List<Runnable> runnableList) {
        for(Runnable runnable:runnableList){
            //mCacheThreadExecutor.submit(runnable);
            mCacheThreadExecutor.execute(runnable);
        }
    }
    public void ExecutorServiceThread(Runnable runnable) {
            mCacheThreadExecutor.execute(runnable);
    }

    public void shutdown(){
        mCacheThreadExecutor.shutdown();
    }
}
