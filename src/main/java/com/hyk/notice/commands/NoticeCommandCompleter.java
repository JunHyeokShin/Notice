package com.hyk.notice.commands;

import com.hyk.notice.NoticePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NoticeCommandCompleter implements TabCompleter {
    private final NoticePlugin plugin;

    public NoticeCommandCompleter(NoticePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("notice")) {
            if (args.length == 1) {
                return plugin.getNoticeCommand().getSubCommandsKeys();
            } else if (args.length == 2) {
                if (plugin.getNoticeCommand().getSubCommands().containsKey(args[0].toLowerCase())) {
                    return plugin.getNoticeCommand().getSubCommands().get(args[0].toLowerCase()).getArguments();
                }
            }
        }
        return null;
    }
}
