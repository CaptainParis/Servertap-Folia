package io.servertap.api.platform.scheduler;

/**
 * Interface for platform-specific scheduler operations
 */
public interface SchedulerAdapter {

    /**
     * Run a task asynchronously
     * @param runnable The task to run
     */
    void runAsync(Runnable runnable);

    /**
     * Run a task synchronously
     * @param runnable The task to run
     */
    void runSync(Runnable runnable);

    /**
     * Run a task asynchronously after a delay
     * @param runnable The task to run
     * @param delayTicks The delay in ticks
     */
    void runAsyncLater(Runnable runnable, long delayTicks);

    /**
     * Run a task synchronously after a delay
     * @param runnable The task to run
     * @param delayTicks The delay in ticks
     */
    void runSyncLater(Runnable runnable, long delayTicks);

    /**
     * Run a task asynchronously at a fixed rate
     * @param runnable The task to run
     * @param delayTicks The delay in ticks
     * @param periodTicks The period in ticks
     * @return A task ID that can be used to cancel the task
     */
    int runAsyncRepeating(Runnable runnable, long delayTicks, long periodTicks);

    /**
     * Run a task synchronously at a fixed rate
     * @param runnable The task to run
     * @param delayTicks The delay in ticks
     * @param periodTicks The period in ticks
     * @return A task ID that can be used to cancel the task
     */
    int runSyncRepeating(Runnable runnable, long delayTicks, long periodTicks);

    /**
     * Cancel a task
     * @param taskId The task ID
     */
    void cancelTask(int taskId);

    /**
     * Cancel all tasks
     */
    void cancelAllTasks();
}
