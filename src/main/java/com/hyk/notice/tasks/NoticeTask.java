package com.hyk.notice.tasks;

import com.hyk.notice.NoticePlugin;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

public class NoticeTask implements Runnable {
    private final NoticePlugin plugin;
    private final Random randomGenerator = new Random();
    private final BukkitScheduler scheduler;
    private boolean isTaskRunning = false;
    private int indexOfMessageNotifiedRecently = -1;

    public NoticeTask(NoticePlugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
    }

    @Override
    public void run() {
        indexOfMessageNotifiedRecently++;
        if (plugin.getNoticeConfig().isShuffle()) {
            indexOfMessageNotifiedRecently = randomGenerator.nextInt(plugin.getNoticeConfig().getNumberOfMessages());
        } else if (indexOfMessageNotifiedRecently >= plugin.getNoticeConfig().getNumberOfMessages()) {
            indexOfMessageNotifiedRecently = 0;
        }
        notice(indexOfMessageNotifiedRecently);
    }

    public boolean canTaskRun() {
        return plugin.getNoticeConfig().isEnabled() && plugin.getServer().getOnlinePlayers().size() > 0 && plugin.getNoticeConfig().getNumberOfMessages() > 0 && !isTaskRunning;
    }

    public void runTask() {
        scheduler.scheduleSyncRepeatingTask(plugin, this, plugin.getNoticeConfig().getInterval() * 20L, plugin.getNoticeConfig().getInterval() * 20L);
        isTaskRunning = true;
    }

    public void stopTask() {
        scheduler.cancelTasks(plugin);
        isTaskRunning = false;
    }

    public void notice(String message) {
        plugin.getServer().getConsoleSender().sendMessage(replaceColorCodes(plugin.getNoticeConfig().getPrefix() + message));

        message = replaceCodes(message);
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.hasPermission("notice.receiver")) {
                player.sendMessage(replaceColorCodes(plugin.getNoticeConfig().getPrefix()) + replaceCodes(message, player));
            }
        }
    }

    public void notice(int index) {
        notice(plugin.getNoticeConfig().getMessage(index));
    }

    public boolean isTaskRunning() {
        return isTaskRunning;
    }

    public void taskRunning(boolean isTaskRunning) {
        this.isTaskRunning = isTaskRunning;
    }

    public int getIndexOfMessageNotifiedRecently() {
        return indexOfMessageNotifiedRecently;
    }

    private String replaceCodes(String message) {
        message = replaceColorCodes(message);
        message = message.replace("{$online}", Integer.toString(plugin.getServer().getOnlinePlayers().size()));
        message = message.replace("{$maxplayers}", Integer.toString(plugin.getServer().getMaxPlayers()));
        return message;
    }

    private String replaceCodes(String message, Player player) {
        message = message.replace("{$player}", player.getName());
        message = message.replace("{$health}", (int) player.getHealth() + "/" + (int) player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
        message = message.replace("{$world}", player.getWorld().getName());
        message = message.replace("{$location}", player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
        return message;
    }

    public static String replaceColorCodes(String message) {
        message = message.replace("&0", ChatColor.BLACK.toString());
        message = message.replace("&1", ChatColor.DARK_BLUE.toString());
        message = message.replace("&2", ChatColor.DARK_GREEN.toString());
        message = message.replace("&3", ChatColor.DARK_AQUA.toString());
        message = message.replace("&4", ChatColor.DARK_RED.toString());
        message = message.replace("&5", ChatColor.DARK_PURPLE.toString());
        message = message.replace("&6", ChatColor.GOLD.toString());
        message = message.replace("&7", ChatColor.GRAY.toString());
        message = message.replace("&8", ChatColor.DARK_GRAY.toString());
        message = message.replace("&9", ChatColor.BLUE.toString());
        message = message.replace("&a", ChatColor.GREEN.toString());
        message = message.replace("&b", ChatColor.AQUA.toString());
        message = message.replace("&c", ChatColor.RED.toString());
        message = message.replace("&d", ChatColor.LIGHT_PURPLE.toString());
        message = message.replace("&e", ChatColor.YELLOW.toString());
        message = message.replace("&f", ChatColor.WHITE.toString());
        message = message.replace("&k", ChatColor.MAGIC.toString());
        message = message.replace("&l", ChatColor.BOLD.toString());
        message = message.replace("&m", ChatColor.STRIKETHROUGH.toString());
        message = message.replace("&n", ChatColor.UNDERLINE.toString());
        message = message.replace("&o", ChatColor.ITALIC.toString());
        message = message.replace("&r", ChatColor.RESET.toString());
        return message;
    }
}
