package com.evolutionnext.demo.structuredconcurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.atomic.AtomicLong;

public class MillionsOfTasks {
    private final AtomicLong atomicLong;

    public MillionsOfTasks() {
        atomicLong = new AtomicLong();
    }

    public void submit() throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            for (int i = 0; i < 1000000; i++) {
                scope.fork(() -> {
                    System.out.printf("Freddy Rules - %d on Thread[%s]%n", atomicLong.incrementAndGet(), Thread.currentThread());
                    return 3 + 3;
                });
            }
            scope.join().throwIfFailed();
            System.out.println("Complete");
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MillionsOfTasks millionsOfTasks = new MillionsOfTasks();
        millionsOfTasks.submit();
    }
}
