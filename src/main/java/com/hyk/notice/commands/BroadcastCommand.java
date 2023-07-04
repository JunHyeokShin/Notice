package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements SubCommand {
    private final NoticePlugin plugin;

    public BroadcastCommand(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int index;
        if (sender.hasPermission(getPermission())) {
            if (plugin.getNoticeConfig().getNumberOfMessages() > 0) {
                if (args.length >= 2) {
                    try {
                        index = Integer.parseInt(args[1]) - 1;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    if (index >= 0 && index < plugin.getNoticeConfig().getNumberOfMessages()) {
                        plugin.getNoticeTask().notice(index);
                    } else {
                        sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "The index is out of range.");
                    }
                } else {
                    plugin.getNoticeTask().run();
                }
            } else {
                sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "There are no messages to broadcast.");
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
