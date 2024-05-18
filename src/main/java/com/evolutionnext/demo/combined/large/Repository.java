package com.evolutionnext.demo.combined.large;

import java.util.NoSuchElementException;

public class Repository {
    private final ScopedValue<String>  key;

    public Repository(ScopedValue<String> key) {
        this.key = key;
    }

    public Long persist() {
        printThreadAndKey("In Repository#persist()");
        return 50L;
    }

    public Employee find() {
        return ScopedValue.where(key, "Buenos Dias").get(() -> {
            printThreadAndKey("In Repository#find()");
            return new Employee("James", "Gosling");
        });
    }

    private void printThreadAndKey(String label) {
        try {
            System.out.format("%s: %s contains key \"%s\"\n", label, Thread.currentThread(), key.get());
        } catch (NoSuchElementException e) {
            System.out.format("%s: %s has no key!\n", label, Thread.currentThread());
        }
    }
}
