package net.qsef1256.capstone2022server.schedule;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 스케줄러, 종료시 수동으로 정지 시켜야 합니다.
 *
 * <p>내부 시간은 한국 시간 (Asia/Seoul) 을 기준으로 합니다.</p>
 *
 * @implNote call shutdown() at stop
 * @see <a href=https://alwayspr.tistory.com/32>See Reference</a>
 */
@Slf4j
public class DiaScheduler {

    private static final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    @Getter
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(DiaScheduler::shutdown));
    }

    /**
     * 자정에 스케줄을 실행합니다.
     *
     * @param runnable Runnable to schedule
     */
    public static void executePerDay(Runnable runnable) {
        executePerTime(runnable, 0, 0, 0);
    }

    /**
     * 지정된 시간에 스케줄을 실행합니다.
     *
     * @param runnable Runnable to schedule
     * @param hour     0 ~ 23
     * @param minute   0 ~ 59
     * @param second   0 ~ 59
     */
    public static void executePerTime(Runnable runnable, int hour, int minute, int second) {
        ZonedDateTime nextExecutionTime = getNextExecutionTime(hour, minute, second);

        long delay = getDiffFromNow(nextExecutionTime);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                runnable.run();
            } catch (Exception e) {
                log.error("Failed to execute task", e);
            }
        }, delay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

    private static ZonedDateTime getNextExecutionTime(int hour, int minute, int second) {
        ZonedDateTime nextExecutionTime = now.withHour(hour).withMinute(minute).withSecond(second);
        if (isOverDay(nextExecutionTime)) nextExecutionTime = nextExecutionTime.plusDays(1);

        return nextExecutionTime;
    }

    @Contract(pure = true)
    private static boolean isOverDay(ZonedDateTime nextExecutionTime) {
        return now.compareTo(nextExecutionTime) > 0;
    }

    private static long getDiffFromNow(ZonedDateTime nextExecutionTime) {
        return Duration.between(now, nextExecutionTime).getSeconds();
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
