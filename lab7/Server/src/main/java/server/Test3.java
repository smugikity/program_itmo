package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {

    private String threadName ;

    public Task(String threadName) {
        this.threadName =  threadName;
    }

    public void run() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" "
                + Thread.currentThread().getId());
    }

    public String getName() {
        return threadName;
    }

    public void setName(String threadName) {
        this.threadName = threadName;
    }

}

public class Test3 {

    public static void main(String args[]) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Task task = new Task("Task 0");
        for (int i=1; i<=3 ; i++) {
            Task tasks = new Task("Task"+ i);
            System.out.println("Started: "+tasks.getName());
            service.execute(tasks);
        }

        for (int i=1; i<=7; i++) {
            service.execute(task);
            TimeUnit.SECONDS.sleep(0);
        }

        service.shutdown();
    }

}