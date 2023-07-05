package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class InfoCommand implements SubCommand {
    private final NoticePlugin plugin;
    private final List<String> arguments = new ArrayList<>();

    public InfoCommand(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(getPermission())) {
            sender.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "====[" + ChatColor.RED + ChatColor.BOLD + "Notice Plugin Information" + ChatColor.GOLD + ChatColor.BOLD + "]====");
            sender.sendMessage(ChatColor.GOLD + "Author: " + ChatColor.RESET + "JunHyeokShin(HYK)");
            sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.RESET + "v" + plugin.getPluginMeta().getVersion());
            sender.sendMessage(ChatColor.GOLD + "Enabled: " + ChatColor.RESET + (plugin.getNoticeConfig().isEnabled() ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No"));
            sender.sendMessage(ChatColor.GOLD + "Notifications: " + (plugin.getNoticeTask().isTaskRunning() ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No") + ChatColor.GRAY + " - Automatic notifications will be disabled if there are no players or registered messages.");
            sender.sendMessage(ChatColor.GOLD + "Shuffle: " + ChatColor.RESET + (plugin.getNoticeConfig().isShuffle() ? ChatColor.GREEN + "Yes" : ChatColor.RED + "No"));
            sender.sendMessage(ChatColor.GOLD + "Prefix: " + ChatColor.RESET + plugin.getNoticeTask().replaceColorCodes(plugin.getNoticeConfig().getPrefix()));
            sender.sendMessage(ChatColor.GOLD + "Interval: " + ChatColor.RESET + plugin.getNoticeConfig().getInterval() + " seconds");
            sender.sendMessage(ChatColor.GOLD + "Message: " + ChatColor.RESET + (plugin.getNoticeTask().getIndexOfMessageNotifiedRecently() + 1) + "/" + plugin.getNoticeConfig().getNumberOfMessages());
        } else {
            sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "You don't have permission to access this command.");
        }
        return true;
    }

    @Override
    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public String getPermission() {
        return "notice.admin";
    }
}
