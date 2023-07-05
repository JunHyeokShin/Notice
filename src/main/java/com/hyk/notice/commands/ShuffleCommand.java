package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ShuffleCommand implements SubCommand {
    private final NoticePlugin plugin;
    private final List<String> arguments = new ArrayList<>();

    public ShuffleCommand(NoticePlugin plugin) {
        this.plugin = plugin;
        arguments.add("on");
        arguments.add("off");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(getPermission())) {
            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("on")) {
                    plugin.getNoticeConfig().setShuffle(true);
                    sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "Shuffle mode has been enabled.");
                } else if (args[1].equalsIgnoreCase("off")) {
                    plugin.getNoticeConfig().setShuffle(false);
                    sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "Shuffle mode has been disabled.");
                } else {
                    return false;
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
    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public String getPermission() {
        return "notice.admin";
    }
}
