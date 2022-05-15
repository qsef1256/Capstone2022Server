package net.qsef1256.capstone2022server.schedule;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DiaScheduler {

    @Getter
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void executePerDay(Runnable runnable) {
        long midnight = LocalDateTime.now().until(LocalDate.now().plusDays(1).atStartOfDay(), ChronoUnit.MINUTES);
        scheduler.scheduleAtFixedRate(runnable, midnight, TimeUnit.DAYS.toMinutes(1), TimeUnit.MINUTES);
    }

    @NotNull
    public static ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        return scheduler.schedule(runnable, delay, timeUnit);
    }

    @NotNull
    public static ScheduledFuture<?> schedule(Runnable runnable, long second) {
        return schedule(runnable, second, TimeUnit.SECONDS);
    }

    public static void shutdown() {
        scheduler.shutdown();
    }

}
