package com.example.stackfarm.myapplication.opening;

import android.app.Activity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Finishes an activity after a period of inactivity.
 */
public final class InactivityTimer {
 
    private static final int INACTIVITY_DELAY_SECONDS = 4; //5分钟后自动关闭目标activity
    /**
     * 创建只有一条线程的线程池，他可以在指定延迟后执行线程任务,其中一个构造方法可以传入ThreadFractory
     * JDK 1.5之后有了ScheduledExecutorService，不建议你再使用java.util.Timer，
     * 因为它无论功能性能都不如ScheduledExecutorService。
     */
    private final ScheduledExecutorService inactivityTimer = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
    private final Activity activity;
    //表示ScheduledExecutorService中提交了任务的返回结果
    private ScheduledFuture<?> inactivityFuture = null;
 
    public InactivityTimer(Activity activity) {
        this.activity = activity;
        onActivity();
    }
 
    public void onActivity() {
        cancel();
        //5分钟后执行关闭操作
        inactivityFuture = inactivityTimer.schedule(new FinishListener(activity), INACTIVITY_DELAY_SECONDS, TimeUnit.SECONDS);
    }
 
    private void cancel() {
        if (inactivityFuture != null) {
            inactivityFuture.cancel(true);
            inactivityFuture = null;
        }
    }
 
    public void shutdown() {
        cancel();
        inactivityTimer.shutdown();
    }
 
    /**
     * 通过自定义ThreadFactory创建一个守护线程
     */
    private static final class DaemonThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            //设置为守护线程,用于守护当前的UI线程
            thread.setDaemon(true);
            return thread;
        }
    }
 
}
