package com.evolutionnext.demo.virtualthreads;

/*
 * 1. Immediately start a virtual thread
 */
public class ImmediatelyStartAVirtualThread {
    public static void main(String[] args) throws InterruptedException {
        Thread startedThread = Thread.ofVirtual().start(() -> {
            System.out.printf("Starting Thread in %s", Thread.currentThread());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Waiting for Thread to finish");
        startedThread.join();
    }
}
