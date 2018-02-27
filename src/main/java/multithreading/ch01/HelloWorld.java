package multithreading.ch01;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloWorld {

  static final int THREAD_SIZE = 3;

  public static void main(String[] args) {
    ThreadHello hello = new ThreadHello(5);
//    AtomicThreadHello hello = new AtomicThreadHello(5);
    Thread[] threads = new Thread[THREAD_SIZE];

    for (int i = 0; i < THREAD_SIZE; i++) {
      threads[i] = new Thread(hello);
    }

    for (Thread t : threads) {
      t.start();
    }

    try {
      for (Thread t : threads) {
        t.join();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class ThreadHello implements Runnable {

  private int counter;
//  volatile private int counter;

  public ThreadHello(int counter) {
    this.counter = counter;
  }

  public void run() {
    for (int i = 0; i < HelloWorld.THREAD_SIZE; i++) {
      try {
        TimeUnit.MILLISECONDS.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      counter++;
//      synchronized (this) {
//        counter++;
//      }

      System.out.printf("%s: %d\n", Thread.currentThread().getName(), counter);
    }
  }
}

class AtomicThreadHello implements Runnable {

  private AtomicInteger counter;

  public AtomicThreadHello(int counter) {
    this.counter = new AtomicInteger(counter);
  }

  @Override
  public void run() {
    for (int i = 0; i < HelloWorld.THREAD_SIZE; i++) {
      try {
        TimeUnit.MILLISECONDS.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      counter.getAndIncrement();

      System.out.printf("%s: %d\n", Thread.currentThread().getName(), counter.intValue());
    }
  }

}