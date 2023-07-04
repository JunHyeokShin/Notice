package com.hyk.notice.listeners;

import com.hyk.notice.NoticePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    private final NoticePlugin plugin;

    public PlayerQuit(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (plugin.getServer().getOnlinePlayers().size() <= 1 && plugin.getNoticeTask().isTaskRunning()) {
            plugin.getNoticeTask().stopTask();
            plugin.getLogger().info("Notification task stopped: Notifications will no longer be sent.");
        }
    }
}
