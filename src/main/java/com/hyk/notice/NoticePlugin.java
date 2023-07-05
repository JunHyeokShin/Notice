package com.hyk.notice;

import com.hyk.notice.commands.*;
import com.hyk.notice.listeners.*;
import com.hyk.notice.config.NoticeConfig;
import com.hyk.notice.tasks.NoticeTask;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public final class NoticePlugin extends JavaPlugin {
    private Logger logger = getLogger();
    private final NoticeTask noticeTask = new NoticeTask(this);
    private final NoticeConfig noticeConfig = new NoticeConfig(this);
    private final NoticeCommand noticeCommand = new NoticeCommand();
    private final NoticeCommandCompleter noticeCommandCompleter = new NoticeCommandCompleter(this);

    public NoticePlugin() {
    }

    public void onEnable() {
        this.noticeConfig.initConfig();
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        this.getCommand("notice").setExecutor(this.noticeCommand);
        this.getCommand("notice").setTabCompleter(this.noticeCommandCompleter);
        this.noticeCommand.registerCommand(new HelpCommand(this), "help");
        this.noticeCommand.registerCommand(new VersionCommand(this), "version");
        this.noticeCommand.registerCommand(new InfoCommand(this), "info");
        this.noticeCommand.registerCommand(new ListCommand(this), "list");
        this.noticeCommand.registerCommand(new AddCommand(this), "add", "insert");
        this.noticeCommand.registerCommand(new ModifyCommand(this), "modify");
        this.noticeCommand.registerCommand(new DeleteCommand(this), "delete");
        this.noticeCommand.registerCommand(new EnableCommand(this), "on", "off");
        this.noticeCommand.registerCommand(new ShuffleCommand(this), "shuffle");
        this.noticeCommand.registerCommand(new PrefixCommand(this), "prefix");
        this.noticeCommand.registerCommand(new IntervalCommand(this), "interval");
        this.noticeCommand.registerCommand(new BroadcastCommand(this), "broadcast");
        this.noticeCommand.registerCommand(new SayCommand(this), "say");
        this.noticeCommand.registerCommand(new ReloadCommand(this), "reload");

        if (this.noticeTask.canTaskRun()) {
            this.noticeTask.runTask();
        }
    }

    public void onDisable() {
        if (this.noticeTask.isTaskRunning()) {
            this.noticeTask.stopTask();
        }

    }

    public NoticeTask getNoticeTask() {
        return this.noticeTask;
    }

    public NoticeConfig getNoticeConfig() {
        return this.noticeConfig;
    }

    public NoticeCommand getNoticeCommand() {
        return this.noticeCommand;
    }
}
