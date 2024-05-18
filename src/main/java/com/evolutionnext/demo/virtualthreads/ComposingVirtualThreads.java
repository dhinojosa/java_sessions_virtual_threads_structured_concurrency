package com.evolutionnext.demo.virtualthreads;

/*
 * 5. Virtual Threads can be composed
 */
public class ComposingVirtualThreads {
    public static void main(String[] args) throws InterruptedException {
        Thread startedThread = Thread.ofVirtual().start(() -> {
            final var city = getCity();
            Thread innerThread = Thread.ofVirtual().start(() -> {
                final var temperature = getTemperature(city);
                System.out.printf("The temperature for %s is %d", city, temperature);
            });
            try {
                innerThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Waiting for Thread to finish");
        startedThread.join();
    }

    private static String getCity() {
        System.out.printf("[%s] Getting city%n", Thread.currentThread());
        return "Phoenix, Arizona";
    }

    private static int getTemperature(String city) {
        System.out.printf("[%s] Getting temperature for %s%n", Thread.currentThread(), city);
        return 104;
    }
}
