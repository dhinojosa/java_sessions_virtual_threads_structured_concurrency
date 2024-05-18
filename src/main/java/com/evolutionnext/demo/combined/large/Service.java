package com.evolutionnext.demo.combined.large;

import java.util.concurrent.StructuredTaskScope;

public class Service {

    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public String run() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var id = scope.fork(repository::persist);
            var employee = scope.fork(repository::find);
            scope.join();
            return STR."Found id of \{id.get()} and employee \{employee.get()}";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
