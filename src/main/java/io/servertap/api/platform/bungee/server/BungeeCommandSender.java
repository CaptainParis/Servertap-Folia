package io.servertap.api.platform.bungee.server;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

/**
 * BungeeCord command sender implementation for capturing command output
 */
public class BungeeCommandSender implements CommandSender {

    private final Consumer<String> messageConsumer;

    /**
     * Create a new BungeeCommandSender
     * @param messageConsumer The message consumer
     */
    public BungeeCommandSender(Consumer<String> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public String getName() {
        return "ServerTap";
    }

    @Override
    public void sendMessage(String message) {
        messageConsumer.accept(message);
    }

    @Override
    public void sendMessages(String... messages) {
        for (String message : messages) {
            messageConsumer.accept(message);
        }
    }

    @Override
    public void sendMessage(BaseComponent... message) {
        for (BaseComponent component : message) {
            messageConsumer.accept(component.toLegacyText());
        }
    }

    @Override
    public void sendMessage(BaseComponent message) {
        messageConsumer.accept(message.toLegacyText());
    }

    @Override
    public Collection<String> getGroups() {
        return Collections.emptyList();
    }

    @Override
    public void addGroups(String... groups) {
        // Do nothing
    }

    @Override
    public void removeGroups(String... groups) {
        // Do nothing
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }

    @Override
    public void setPermission(String permission, boolean value) {
        // Do nothing
    }

    @Override
    public Collection<String> getPermissions() {
        return Collections.emptyList();
    }
}
