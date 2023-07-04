package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpCommand implements SubCommand {
    private final NoticePlugin plugin;

    public HelpCommand(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(getPermission())) {
            sender.sendMessage(ChatColor.GOLD + "/notice [help]: " + ChatColor.RESET + "Lists all available plugin commands.");
            sender.sendMessage(ChatColor.GOLD + "/notice version: " + ChatColor.RESET + "Displays the current version of the plugin.");
            sender.sendMessage(ChatColor.GOLD + "/notice info: " + ChatColor.RESET + "Shows information and status of the Notice plugin.");
            sender.sendMessage(ChatColor.GOLD + "/notice list [page]: " + ChatColor.RESET + "Lists the messages.");
            sender.sendMessage(ChatColor.GOLD + "/notice add <message>: " + ChatColor.RESET + "Adds a new message.");
            sender.sendMessage(ChatColor.GOLD + "/notice insert <index> <message>: " + ChatColor.RESET + "Inserts a new message at the specified index.");
            sender.sendMessage(ChatColor.GOLD + "/notice modify <index> <message>: " + ChatColor.RESET + "Modifies the message at the specified index.");
            sender.sendMessage(ChatColor.GOLD + "/notice delete <index>: " + ChatColor.RESET + "Deletes the message at the specified index.");
            sender.sendMessage(ChatColor.GOLD + "/notice delete all: " + ChatColor.RESET + "Deletes all messages.");
            sender.sendMessage(ChatColor.GOLD + "/notice <on|off>: " + ChatColor.RESET + "Enables or disables notifications.");
            sender.sendMessage(ChatColor.GOLD + "/notice shuffle <on|off>: " + ChatColor.RESET + "Enables or disables shuffle mode.");
            sender.sendMessage(ChatColor.GOLD + "/notice prefix [prefix]: " + ChatColor.RESET + "Sets a custom message prefix. To remove the prefix, omit [prefix].");
            sender.sendMessage(ChatColor.GOLD + "/notice interval <interval>: " + ChatColor.RESET + "Sets the duration between each notification in seconds.");
            sender.sendMessage(ChatColor.GOLD + "/notice broadcast [index]: " + ChatColor.RESET + "Notifies the message at the specified index or next message immediately.");
            sender.sendMessage(ChatColor.GOLD + "/notice say <message>: " + ChatColor.RESET + "Notifies the specified message once.");
            sender.sendMessage(ChatColor.GOLD + "/notice reload: " + ChatColor.RESET + "Reloads plugin.");
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
