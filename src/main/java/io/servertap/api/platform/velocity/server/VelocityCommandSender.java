package io.servertap.api.platform.velocity.server;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.permission.Tristate;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Velocity command sender implementation for capturing command output
 */
public class VelocityCommandSender implements CommandSource {

    private final Consumer<String> messageConsumer;

    /**
     * Create a new VelocityCommandSender
     * @param messageConsumer The message consumer
     */
    public VelocityCommandSender(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        messageConsumer.accept(LegacyComponentSerializer.legacySection().serialize(message));
    }

    @Override
    public void sendMessage(@NotNull Component message) {
        messageConsumer.accept(LegacyComponentSerializer.legacySection().serialize(message));
    }

    @Override
    public @NotNull Tristate getPermissionValue(@NotNull String permission) {
        return Tristate.TRUE;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return true;
    }
}
