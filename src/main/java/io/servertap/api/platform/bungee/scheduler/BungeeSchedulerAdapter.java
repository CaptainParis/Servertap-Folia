package io.servertap.api.platform.bungee.scheduler;

import io.servertap.api.platform.scheduler.SchedulerAdapter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * BungeeCord implementation of the scheduler adapter
 */
public class BungeeSchedulerAdapter implements SchedulerAdapter {

    private final Plugin plugin;
    private final TaskScheduler scheduler;
    private final Map<Integer, ScheduledTask> tasks = new HashMap<>();
    private int taskCounter = 0;

    /**
     * Create a new BungeeSchedulerAdapter
     * @param plugin The BungeeCord plugin instance
     */
    public BungeeSchedulerAdapter(Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getProxy().getScheduler();
    }

    @Override
    public void runAsync(Runnable runnable) {
        scheduler.runAsync(plugin, runnable);
    }

    @Override
    public void runSync(Runnable runnable) {
        // BungeeCord doesn't have a concept of sync tasks, all tasks are async
        scheduler.runAsync(plugin, runnable);
    }

    @Override
    public void runAsyncLater(Runnable runnable, long delayTicks) {
        // Convert ticks to milliseconds (assuming 20 ticks per second)
        long delayMillis = delayTicks * 50;
        scheduler.schedule(plugin, runnable, delayMillis, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runSyncLater(Runnable runnable, long delayTicks) {
        // BungeeCord doesn't have a concept of sync tasks, all tasks are async
        runAsyncLater(runnable, delayTicks);
    }

    @Override
    public int runAsyncRepeating(Runnable runnable, long delayTicks, long periodTicks) {
        // Convert ticks to milliseconds (assuming 20 ticks per second)
        long delayMillis = delayTicks * 50;
        long periodMillis = periodTicks * 50;
        
        int taskId = ++taskCounter;
        ScheduledTask task = scheduler.schedule(plugin, runnable, delayMillis, periodMillis, TimeUnit.MILLISECONDS);
        tasks.put(taskId, task);
        
        return taskId;
    }

    @Override
    public int runSyncRepeating(Runnable runnable, long delayTicks, long periodTicks) {
        // BungeeCord doesn't have a concept of sync tasks, all tasks are async
        return runAsyncRepeating(runnable, delayTicks, periodTicks);
    }

    @Override
    public void cancelTask(int taskId) {
        ScheduledTask task = tasks.remove(taskId);
        if (task != null) {
            task.cancel();
        }
    }

    @Override
    public void cancelAllTasks() {
        scheduler.cancel(plugin);
        tasks.clear();
    }
}
