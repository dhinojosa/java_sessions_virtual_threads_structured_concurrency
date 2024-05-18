package com.evolutionnext.demo.combined.large;

import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

public class Application {
    private static ScopedValue<String> KEY = ScopedValue.newInstance();

    public static void main(String[] args) {
        ScopedValue.where(KEY, "Hello").run(task());
        ScopedValue.where(KEY, "Bon Jour").run(task());
    }

    @NotNull
    private static Runnable task() {
        return () -> {
            printThreadAndKey("In task, before structured scope");
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

                printThreadAndKey("In task, in structured scope");
                Supplier<String> string = scope.fork(() -> {
                    Repository repository = new Repository(KEY);
                    Service service = new Service(repository);
                    return service.run();
                });
                scope.join();
                System.out.println(STR."Result is \{string.get()}");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            printThreadAndKey("In task, after structured scope");
        };
    }

    private static void printThreadAndKey(String label) {
        try {
            System.out.format("%s: %s contains key \"%s\"\n", label, Thread.currentThread(), Application.KEY.get());
        } catch (NoSuchElementException e) {
            System.out.format("%s: %s has no key!\n", label, Thread.currentThread());
        }
    }
}
