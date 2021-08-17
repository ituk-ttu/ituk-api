package ee.ituk.api.mail.gmail;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
public class AsyncRunner {

    @Async // todo conf pools
    public <T> CompletableFuture<T> runInAsync(Supplier<T> supplier) {
        return AsyncResult.forValue(supplier.get()).completable();
    }
}
