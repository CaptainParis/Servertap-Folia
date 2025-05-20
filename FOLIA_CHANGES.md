# Folia Support Implementation

This document outlines the changes made to add Folia support to the ServerTap plugin.

## Overview

Folia is a fork of Paper that adds regionized multithreading to Minecraft servers. This allows servers with many spread-out players to utilize multiple CPU cores efficiently. However, it requires plugins to be adapted to work with its threading model.

## Changes Made

### 1. SchedulerUtils Class

Created a utility class to abstract scheduler operations for both Bukkit/Paper and Folia:

- `isFolia()`: Detects if the server is running Folia
- `runTask()`: Runs a task once on the appropriate scheduler
- `runRepeatingTask()`: Runs a repeating task on the appropriate scheduler
- `runTaskAsynchronously()`: Runs an asynchronous task on the appropriate scheduler
- `runTaskLater()`: Runs a delayed task on the appropriate scheduler

All methods include comprehensive error handling and logging.

### 2. WebhookEventListener

Updated to use the `SchedulerUtils.runTaskAsynchronously()` method instead of directly using Bukkit's scheduler or Folia's AsyncScheduler.

### 3. ServerExecCommandSender

Enhanced with better error handling and updated to use a more consistent approach for both Bukkit and Folia environments.

### 4. Metrics

Simplified the metrics submission by using the `SchedulerUtils.runTask()` method.

### 5. ServerTapMain

Already using `SchedulerUtils.runRepeatingTask()` for the TPS counter, added clarifying comments.

### 6. plugin.yml

Added `folia-supported: true` to indicate Folia compatibility.

## Testing

Manual testing is required to verify the Folia integration. See `src/test/manual-tests.md` for detailed testing procedures.

## Benefits

1. **Improved Error Handling**: All scheduler operations now include proper error handling and logging
2. **Code Consistency**: Unified approach to scheduling tasks across different server types
3. **Maintainability**: Centralized scheduler logic in one utility class
4. **Performance**: Proper utilization of Folia's threading model for better performance on multi-core systems

## Future Improvements

1. **Region-Aware Operations**: Consider implementing region-specific operations for entity and world interactions
2. **Testing Framework**: Develop a more comprehensive testing framework for Folia compatibility
3. **Documentation**: Expand documentation on how to use ServerTap with Folia
