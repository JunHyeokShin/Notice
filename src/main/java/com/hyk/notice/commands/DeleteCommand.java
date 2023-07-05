package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class DeleteCommand implements SubCommand {
    private final NoticePlugin plugin;
    private final List<String> arguments = new ArrayList<>();

    public DeleteCommand(NoticePlugin plugin) {
        this.plugin = plugin;
        arguments.add("all");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int index;
        if (sender.hasPermission(getPermission())) {
            if (plugin.getNoticeConfig().getNumberOfMessages() > 0) {
                if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("all")) {
                        plugin.getNoticeConfig().deleteAllMessages();
                        sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "All messages have been deleted.");
                    } else {
                        try {
                            index = Integer.parseInt(args[1]) - 1;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                        if (index >= 0 && index < plugin.getNoticeConfig().getNumberOfMessages()) {
                            plugin.getNoticeConfig().deleteMessage(index);
                            sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "The message has been deleted.");
                        } else {
                            sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "The index is out of range.");
                        }
                    }
                } else {
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "There are no messages to delete.");
            }
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
