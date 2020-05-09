import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 关闭线程是为了防止其他任务复用该线程的数据，防止出错
 * 不建议人为控制线程优先级，大多数情况应该通过cpu自行调控
 */
public class RunTread {

    /**
     *
     * @param args
     * 开启main函数相当于开启了一个全新的线程
     * 与其他的线程运行并不重合
     * 所以开启其它线程任务的时候不会阻塞main函数
     * 因为jdk的不相同，不能依赖输出的一致性编写代码
     */
    public static void main(String []args){
        /*Run run = new Run();
        run.run();*/
        /*LiftOff liftOff = new LiftOff(10);
        liftOff.run();*/
        /**
         * 利用thread显示的开启线程服务
         */
        /*Thread thread = new Thread(new LiftOff());
        //此句设定是其为后台线程
        thread.setDaemon(true);
        thread.start();
        System.out.println("Waiting for LiftOff");*/
        /**
         * 利用线程执行器开启服务
         * newCachedThreadPool开启服务的时候不会限制线程数量，有多少任务就可以新建多少线程
         * newFixedThreadPool开启服务会限制线程的数量，线程池中存有特定数量的线程，无法滥用资源
         */
        /*ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i++){
            exec.execute(new LiftOff());
        }
        exec.shutdown();*/
        /**
         * 针对Executor接口的应用
         */
        /*Executor1 executor1 =  new Executor1();
        executor1.execute(new LiftOff());*/

        /**
         * 利用Callable有目的性的返回数据
         */
        ExecutorService executorService = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();
        for(int i = 0; i < 10; i++){
            results.add(executorService.submit(new TaskWithResult(i)));
        }
        for(Future<String> fs : results){
            try{
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }finally {
                executorService.shutdown();
            }
        }
    }

}

class TaskWithResult implements Callable<String> {

    private int id;
    public TaskWithResult(int id){
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        return "result of TaskWithResult" + id;
    }
}

class Executor1 implements Executor{

    @Override
    public void execute(Runnable command) {

        command.run();

    }
}

class Run implements Runnable{

    @Override
    public void run() {
        System.out.println("线程一直在运行哦");
        Thread.yield();
    }
}

class LiftOff implements Runnable {

    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {
    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + "),";
    }

    public void run() {
        while (countDown-- > 0) {
            System.out.print(status());
            Thread.yield();
        }

    }
}
