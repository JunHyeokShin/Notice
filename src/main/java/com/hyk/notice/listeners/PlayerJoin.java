package com.hyk.notice.listeners;

import com.hyk.notice.NoticePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private final NoticePlugin plugin;

    public PlayerJoin(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.getNoticeTask().canTaskRun()) {
            plugin.getNoticeTask().runTask();
            plugin.getLogger().info("Notification task resumed: Notifications will now be sent again.");
        }
    }
}
