package com.hyk.notice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    boolean onCommand(CommandSender sender, Command command, String label, String[] args);

    List<String> getArguments();

    String getPermission();
}
