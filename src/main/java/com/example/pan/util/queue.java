package com.example.pan.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  阻塞队列
 * </p>
 *
 * @author daShen
 * @since 2022-04-06
 */
public class queue {
    public static void main1(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
        System.out.println(queue.add(1));
        System.out.println(queue.add(2));
        System.out.println(queue.add(3));

        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue);

        System.out.println(queue.offer(4));
        System.out.println(queue.offer(5));
        System.out.println(queue.offer(6));
        System.out.println(queue);
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
//        System.out.println(queue.remove());


        queue.put(1);
        queue.put(2);
        queue.put(3);
//            queue.put(4); //队列满了 一直阻塞

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
//        System.out.println(queue.take()); //没有这个元素 一直阻塞


        queue.offer(7);
        queue.offer(8);
        queue.offer(9);
        queue.offer(10, 2, TimeUnit.SECONDS); //队列满了  等待超过2秒就退出
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll(2,TimeUnit.SECONDS)); //队列为空 等待超过2秒就退出
        System.out.println(queue);

    }

    public static void main(String[] args) {
        //同步队列
        BlockingQueue<String> synchronousQueue = new SynchronousQueue<>(false);
        new Thread(()->{
            try {
                synchronousQueue.put("a");
                System.out.println(Thread.currentThread().getName()+" put 1");
                synchronousQueue.put("b");
                System.out.println(Thread.currentThread().getName()+" put 2");
                synchronousQueue.put("c");
                System.out.println(Thread.currentThread().getName()+" put 3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"=>"+synchronousQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"=>"+synchronousQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"=>"+synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }
}
