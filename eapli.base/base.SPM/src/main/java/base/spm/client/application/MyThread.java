package base.spm.client.application;

public class MyThread extends Thread {
    public MyThread(Runnable r,String name) {
        super(r,name);
    }

    @Override
    public void run() {
        System.out.println("MyThread - START " + Thread.currentThread().getName());
    }
}
