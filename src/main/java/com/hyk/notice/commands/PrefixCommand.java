package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import com.hyk.notice.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PrefixCommand implements SubCommand {
    private final NoticePlugin plugin;

    public PrefixCommand(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(getPermission())) {
            if (args.length == 1) {
                plugin.getNoticeConfig().setPrefix("");
                sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "The prefix has been removed.");
            } else {
                plugin.getNoticeConfig().setPrefix(Utils.convertArgumentsIntoMessage(args, 1));
                sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "The prefix has been set.");
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
