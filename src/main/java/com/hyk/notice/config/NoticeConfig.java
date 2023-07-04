package com.hyk.notice.config;

import com.hyk.notice.NoticePlugin;

import java.util.List;

public class NoticeConfig {
    private final NoticePlugin plugin;
    private boolean isEnabled;
    private boolean isShuffle;
    private String prefix;
    private long interval;
    private List<String> messages;

    public NoticeConfig(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    public void initConfig() {
        plugin.saveDefaultConfig();
        reloadConfiguration();
    }

    public void reloadConfiguration() {
        plugin.reloadConfig();
        isEnabled = plugin.getConfig().getBoolean("enabled", true);
        isShuffle = plugin.getConfig().getBoolean("shuffle", false);
        prefix = plugin.getConfig().getString("prefix", "&6&l[Notice] &r");
        interval = plugin.getConfig().getLong("interval", 60L);
        if (interval < 0) {
            interval = 60L;
        }
        messages = plugin.getConfig().getStringList("messages");
        plugin.saveConfig();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        plugin.getConfig().set("enabled", this.isEnabled);
        plugin.saveConfig();
        if (plugin.getNoticeTask().canTaskRun()) {
            plugin.getNoticeTask().runTask();
        } else if (plugin.getNoticeTask().isTaskRunning() && !isEnabled) {
            plugin.getNoticeTask().stopTask();
        }
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setShuffle(boolean isShuffle) {
        this.isShuffle = isShuffle;
        plugin.getConfig().set("shuffle", this.isShuffle);
        plugin.saveConfig();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        plugin.getConfig().set("prefix", this.prefix);
        plugin.saveConfig();
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
        plugin.getConfig().set("interval", this.interval);
        plugin.saveConfig();
        if (plugin.getNoticeTask().isTaskRunning()) {
            plugin.getNoticeTask().stopTask();
            plugin.getNoticeTask().runTask();
        }
    }

    public String getMessage(int index) {
        return messages.get(index);
    }

    public int getNumberOfMessages() {
        return messages.size();
    }

    public void addMessage(String message) {
        messages.add(message);
        plugin.getConfig().set("messages", this.messages);
        plugin.saveConfig();
        if (plugin.getNoticeTask().canTaskRun()) {
            plugin.getNoticeTask().runTask();
        }
    }

    public void addMessageAtIndex(int index, String message) {
        messages.add(index, message);
        plugin.getConfig().set("messages", this.messages);
        plugin.saveConfig();
        if (plugin.getNoticeTask().canTaskRun()) {
            plugin.getNoticeTask().runTask();
        }
    }

    public void deleteMessage(int index) {
        messages.remove(index);
        plugin.getConfig().set("messages", this.messages);
        plugin.saveConfig();
        if (plugin.getNoticeTask().isTaskRunning() && getNumberOfMessages() == 0) {
            plugin.getNoticeTask().stopTask();
        }
    }

    public void deleteAllMessages() {
        messages.clear();
        plugin.getConfig().set("messages", this.messages);
        plugin.saveConfig();
        if (plugin.getNoticeTask().isTaskRunning()) {
            plugin.getNoticeTask().stopTask();
        }
    }
}
