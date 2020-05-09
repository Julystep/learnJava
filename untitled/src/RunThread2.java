import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class RunThread2 {

    public static void main(String []args){

        /**
         * 通过线程池与集成的类DaemonThreadFactory创建后台线程
         * 随后为后台线程分配合理的任务
         * 如果线程为后台线程，那么该线程创建的所有线程均为后台线程
         * 如果异常中存在finally，那么后台线程不会执行finally就会结束run()方法
         */
        ExecutorService executorService = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for(int i = 0; i < 10; i++){
            //执行的时候会执行ThreadFactory内部的newThread
            executorService.execute(new DaemonFromFactory());
        }

    }

}

/**
 * 新建线程工厂, 规定默认创建后台线程
 */
class DaemonThreadFactory implements ThreadFactory{
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }

}

class DaemonFromFactory implements Runnable{

    @Override
    public void run() {
        try{
            while (true){
                TimeUnit.MILLISECONDS.sleep(100);
                //Thread.currentThread()用于返回目前执行的线程
                System.out.println(Thread.currentThread() + "  " + this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
