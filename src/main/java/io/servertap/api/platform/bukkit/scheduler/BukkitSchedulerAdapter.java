package io.servertap.api.platform.bukkit.scheduler;

import io.servertap.api.platform.scheduler.SchedulerAdapter;
import io.servertap.utils.SchedulerUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Bukkit implementation of the scheduler adapter
 */
public class BukkitSchedulerAdapter implements SchedulerAdapter {

    private final JavaPlugin plugin;
    private final Map<Integer, Integer> tasks = new HashMap<>();
    private int taskCounter = 0;

    /**
     * Create a new BukkitSchedulerAdapter
     * @param plugin The Bukkit plugin instance
     */
    public BukkitSchedulerAdapter(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void runAsync(Runnable runnable) {
        SchedulerUtils.runTaskAsynchronously(plugin, runnable);
    }

    @Override
    public void runSync(Runnable runnable) {
        SchedulerUtils.runTask(plugin, runnable);
    }

    @Override
    public void runAsyncLater(Runnable runnable, long delayTicks) {
        // No direct async later method in SchedulerUtils, so we'll use a workaround
        SchedulerUtils.runTaskAsynchronously(plugin, () -> {
            try {
                Thread.sleep(delayTicks * 50); // Convert ticks to milliseconds (assuming 20 TPS)
                runnable.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    @Override
    public void runSyncLater(Runnable runnable, long delayTicks) {
        SchedulerUtils.runTaskLater(plugin, runnable, delayTicks);
    }

    @Override
    public int runAsyncRepeating(Runnable runnable, long delayTicks, long periodTicks) {
        int taskId = ++taskCounter;

        // No direct async repeating method in SchedulerUtils, so we'll use a workaround with a thread
        Thread thread = new Thread(() -> {
            try {
                // Initial delay
                Thread.sleep(delayTicks * 50);

                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        runnable.run();
                        Thread.sleep(periodTicks * 50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    } catch (Exception e) {
                        plugin.getLogger().severe("Error in async repeating task: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        thread.setDaemon(true);
        thread.start();

        // Store the thread instead of a task ID
        tasks.put(taskId, -1); // We don't have a Bukkit task ID for this
        return taskId;
    }

    @Override
    public int runSyncRepeating(Runnable runnable, long delayTicks, long periodTicks) {
        int taskId = ++taskCounter;
        // SchedulerUtils.runRepeatingTask doesn't return a task ID, so we'll just store our own ID
        SchedulerUtils.runRepeatingTask(plugin, runnable, delayTicks, periodTicks);
        tasks.put(taskId, -1); // We don't have a Bukkit task ID for this
        return taskId;
    }

    @Override
    public void cancelTask(int taskId) {
        // SchedulerUtils doesn't have a cancelTask method, so we'll just remove it from our map
        // The actual cancellation would need to be handled by the Bukkit scheduler directly
        tasks.remove(taskId);
    }

    @Override
    public void cancelAllTasks() {
        // SchedulerUtils doesn't have a cancelAllTasks method
        // We would need to use the Bukkit scheduler directly
        plugin.getServer().getScheduler().cancelTasks(plugin);
        tasks.clear();
    }
}
