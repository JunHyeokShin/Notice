package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import com.hyk.notice.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AddCommand implements SubCommand {
    private final NoticePlugin plugin;

    public AddCommand(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(getPermission())) {
            if (args[0].equalsIgnoreCase("add")) {
                return onAddCommand(sender, command, label, args);
            } else {
                return onInsertCommand(sender, command, label, args);
            }
        } else {
            sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "You don't have permission to access this command.");
        }
        return true;
    }

    private boolean onAddCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 2) {
            plugin.getNoticeConfig().addMessage(Utils.convertArgumentsIntoMessage(args, 1));
            sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "The message has been added.");
        } else {
            return false;
        }
        return true;
    }

    private boolean onInsertCommand(CommandSender sender, Command command, String label, String[] args) {
        int index;
        if (args.length >= 3) {
            try {
                index = Integer.parseInt(args[1]) - 1;
            } catch (NumberFormatException e) {
                return false;
            }
            if (index >= 0 && index <= plugin.getNoticeConfig().getNumberOfMessages()) {
                plugin.getNoticeConfig().addMessageAtIndex(index, Utils.convertArgumentsIntoMessage(args, 2));
                sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "The message has been added at the specified index.");
            } else {
                sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "The index is out of range.");
                return true;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String getPermission() {
        return "notice.admin";
    }
}
