package com.hyk.notice.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeCommand implements CommandExecutor {
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        boolean isValidArguments;
        if (args.length == 0) {
            subCommands.get("help").onCommand(sender, command, label, args);
            return true;
        } else if (!subCommands.containsKey(args[0].toLowerCase())) {
            isValidArguments = false;
        } else {
            isValidArguments = subCommands.get(args[0].toLowerCase()).onCommand(sender, command, label, args);
        }

        if (!isValidArguments) {
            sender.sendMessage(ChatColor.GOLD + "[Notice] " + ChatColor.RED + "Invalid arguments! use '" + ChatColor.AQUA + "/notice help" + ChatColor.RED + "' command.");
            return true;
        }

        return true;
    }

    public void registerCommand(SubCommand subCommand, String... command) {
        for (String cmd:command) {
            subCommands.put(cmd, subCommand);
        }
    }

    public Map<String, SubCommand> getSubCommands() {
        return subCommands;
    }

    public List<String> getSubCommandsKeys() {
        return new ArrayList<>(subCommands.keySet());
    }
}
