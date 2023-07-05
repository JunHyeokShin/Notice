package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ListCommand implements SubCommand {
    private final NoticePlugin plugin;
    private final List<String> arguments = new ArrayList<>();

    public ListCommand(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission(getPermission())) {
            int page = 1;
            if (plugin.getNoticeConfig().getNumberOfMessages() <= 0) {
                sender.sendMessage(ChatColor.GOLD + "[Notice] " +ChatColor.AQUA + "There are no registered messages.");
                return true;
            } else if (args.length == 1) {
                page = 1;
            } else if (args.length >= 2) {
                try {
                    page = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    return false;
                }
                if (page <= 0 || page > (int) Math.ceil((double) plugin.getNoticeConfig().getNumberOfMessages() / 8)) {
                    sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "The page number is out of range.");
                    return true;
                }
            } else {
                return false;
            }
            showSenderList(sender, page);
        } else {
            sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "You don't have permission to access this command.");
        }
        return true;
    }

    private void showSenderList(CommandSender sender, int page) {
        sender.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "====[" +
                ChatColor.RED + ChatColor.BOLD + String.format("Registered Messages (Page %d/%d)", page, (int) Math.ceil((double) plugin.getNoticeConfig().getNumberOfMessages() / 8)) +
                ChatColor.GOLD + ChatColor.BOLD + "]====");
        int startIndex = Math.abs(page - 1) * 8;
        int stopIndex = Math.min(page * 8, plugin.getNoticeConfig().getNumberOfMessages());
        for (int index = startIndex; index < stopIndex; index++) {
            sender.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + (index + 1) + ". " +
                    ChatColor.RESET + plugin.getNoticeTask().replaceColorCodes(plugin.getNoticeConfig().getMessage(index)));
        }
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
