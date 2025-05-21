package io.servertap.api.platform.velocity.scheduler;

import com.velocitypowered.api.scheduler.ScheduledTask;
import com.velocitypowered.api.scheduler.Scheduler;
import io.servertap.api.platform.scheduler.SchedulerAdapter;
import io.servertap.velocity.VelocityServerTapMain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Velocity implementation of the scheduler adapter
 */
public class VelocitySchedulerAdapter implements SchedulerAdapter {

    private final VelocityServerTapMain plugin;
    private final Map<Integer, Object> tasks = new HashMap<>();
    private int taskCounter = 0;

    /**
     * Create a new VelocitySchedulerAdapter
     * @param plugin The Velocity plugin instance
     */
    public VelocitySchedulerAdapter(VelocityServerTapMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public void runAsync(Runnable runnable) {
        Scheduler scheduler = plugin.getServer().getScheduler();
        scheduler.buildTask(plugin, runnable).schedule();
    }

    @Override
    public void runSync(Runnable runnable) {
        // Velocity doesn't have a concept of sync tasks, all tasks are async
        runAsync(runnable);
    }

    @Override
    public void runAsyncLater(Runnable runnable, long delayTicks) {
        // Convert ticks to milliseconds (assuming 20 ticks per second)
        long delayMillis = delayTicks * 50;
        Scheduler scheduler = plugin.getServer().getScheduler();
        scheduler.buildTask(plugin, runnable)
                .delay(delayMillis, TimeUnit.MILLISECONDS)
                .schedule();
    }

    @Override
    public void runSyncLater(Runnable runnable, long delayTicks) {
        // Velocity doesn't have a concept of sync tasks, all tasks are async
        runAsyncLater(runnable, delayTicks);
    }

    @Override
    public int runAsyncRepeating(Runnable runnable, long delayTicks, long periodTicks) {
        // Convert ticks to milliseconds (assuming 20 ticks per second)
        long delayMillis = delayTicks * 50;
        long periodMillis = periodTicks * 50;

        int taskId = ++taskCounter;
        Scheduler scheduler = plugin.getServer().getScheduler();
        Scheduler.TaskBuilder taskBuilder = scheduler.buildTask(plugin, runnable);

        Object task = taskBuilder
                .delay(delayMillis, TimeUnit.MILLISECONDS)
                .repeat(periodMillis, TimeUnit.MILLISECONDS)
                .schedule();

        tasks.put(taskId, task);
        return taskId;
    }

    @Override
    public int runSyncRepeating(Runnable runnable, long delayTicks, long periodTicks) {
        // Velocity doesn't have a concept of sync tasks, all tasks are async
        return runAsyncRepeating(runnable, delayTicks, periodTicks);
    }

    @Override
    public void cancelTask(int taskId) {
        Object task = tasks.remove(taskId);
        if (task != null && task instanceof ScheduledTask) {
            ((ScheduledTask) task).cancel();
        }
    }

    @Override
    public void cancelAllTasks() {
        for (Object taskObj : tasks.values()) {
            if (taskObj instanceof ScheduledTask) {
                ((ScheduledTask) taskObj).cancel();
            }
        }
        tasks.clear();
    }
}
