package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import com.hyk.notice.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ModifyCommand implements SubCommand {
    private final NoticePlugin plugin;
    private final List<String> arguments = new ArrayList<>();

    public ModifyCommand(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int index;
        if (sender.hasPermission(getPermission())) {
            if (args.length > 2) {
                try {
                    index = Integer.parseInt(args[1]) - 1;
                } catch (NumberFormatException e) {
                    return false;
                }
                if (index >= 0 && index < plugin.getNoticeConfig().getNumberOfMessages()) {
                    plugin.getNoticeConfig().deleteMessage(index);
                    plugin.getNoticeConfig().addMessageAtIndex(index, Utils.convertArgumentsIntoMessage(args, 2));
                    sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.GREEN + "The message has been modified.");
                } else {
                    sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "The index is out of range.");
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
