package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EnableCommand implements SubCommand {
    private final NoticePlugin plugin;

    public EnableCommand(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(getPermission())) {
            if (args[0].equalsIgnoreCase("on")) {
                plugin.getNoticeConfig().setEnabled(true);
                sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "Notifications have been enabled.");
            } else {
                plugin.getNoticeConfig().setEnabled(false);
                sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "Notifications have been disabled.");
            }
        } else {
            sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "You don't have permission to access this command.");
        }
        return true;
    }

    @Override
    public String getPermission() {
        return "notice.admin";
    }
}
