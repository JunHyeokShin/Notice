package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class IntervalCommand implements SubCommand {
    private final NoticePlugin plugin;

    public IntervalCommand(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        long interval;
        if (sender.hasPermission(getPermission())) {
            if (args.length >= 2) {
                try {
                    interval = Long.parseLong(args[1]);
                } catch (NumberFormatException e) {
                    return false;
                }
                if (interval >= 0) {
                    plugin.getNoticeConfig().setInterval(interval);
                    sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "The interval has been changed.");
                } else {
                    sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "The interval must be at least 0.");
                }
            } else {
                return false;
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
