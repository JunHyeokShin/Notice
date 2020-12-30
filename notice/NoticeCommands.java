package notice;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NoticeCommands implements CommandExecutor {
	private final Notice plugin;

	public NoticeCommands(Notice plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean success;

		if(args.length != 0 && !args[0].equalsIgnoreCase("help")) {
			if(args[0].equalsIgnoreCase("version")) {
				success = onVersionCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("info")) {
				success = onInfoCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("list")) {
				success = onListCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("add")) {
				success = onAddCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("addto")) {
				success = onAddToCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("delete")) {
				success = onDeleteCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off")) {
				success = onEnableCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("shuffle")) {
				success = onShuffleCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("prefix")) {
				success = onPrefixCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("interval")) {
				success = onIntervalCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("broadcast")) {
				success = onBroadcastCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("say")) {
				success = onSayCommand(sender, cmd, label, args);
			} else if(args[0].equalsIgnoreCase("reload")) {
				success = onReloadCommand(sender, cmd, label, args);
			} else {
				success = false;
			}
		} else {
			success = onHelpCommand(sender, cmd, label, args);
		}

		if(!success) {
			sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.RED + "Invalid arguments! Use '/notice help' command.");
		}

		return true;
	}

	// /notice, /notice help
	public boolean onHelpCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length <= 1) {
				sender.sendMessage("§6/notice [help]§f: Lists all notice plugin commands.");
				sender.sendMessage("§6/notice version§f: Display the version of plugin.");
				sender.sendMessage("§6/notice info§f: Display info about Notice plugin.");
				sender.sendMessage("§6/notice list [§opage§6]§f: Lists notice messages.");
				sender.sendMessage("§6/notice add <§omessage§6>§f: Add a new notice message.");
				sender.sendMessage("§6/notice addto <§oindex§6> <§omessage§6>§f: Add a new notice message to the index.");
				sender.sendMessage("§6/notice delete <§oindex§6>§f: Delete the notice message in the index.");
				sender.sendMessage("§6/notice delete all§f: Delete all the notice messages.");
				sender.sendMessage("§6/notice <on|off>§f: Enable or disable the notice.");
				sender.sendMessage("§6/notice shuffle <on|off>§f: Enable or disable shuffle mode.");
				sender.sendMessage("§6/notice prefix [§oprefix§6]§f: Set the prefix.");
				sender.sendMessage("§6/notice interval <§ointerval§6>§f: Set the seconds between the notice.");
				sender.sendMessage("§6/notice broadcast [§oindex§6]§f: Notify the index of message or next message.");
				sender.sendMessage("§6/notice say <§omessage§6>§f: Notify the message once.");
				sender.sendMessage("§6/notice reload§f: Reload the config.yml");
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice version
	public boolean onVersionCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length == 1) {
				sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "v0.0.1");
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice info
	public boolean onInfoCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length == 1) {
				sender.sendMessage(ChatColor.AQUA + "§l=====[NOTICE]=====");
				sender.sendMessage(ChatColor.GOLD + "Author: " + ChatColor.WHITE + "JunHyeokShin");
				sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.WHITE + "v0.0.1");
				if(plugin.isNoticeEnabled()) {
					sender.sendMessage(ChatColor.GOLD + "Enabled: " + ChatColor.GREEN + "true");
				} else {
					sender.sendMessage(ChatColor.GOLD + "Enabled: " + ChatColor.GRAY + "false");
				}
				if(plugin.isShuffle()) {
					sender.sendMessage(ChatColor.GOLD + "Shuffle: " + ChatColor.GREEN + "true");
				} else {
					sender.sendMessage(ChatColor.GOLD + "Shuffle: " + ChatColor.GRAY + "false");
				}
				sender.sendMessage(ChatColor.GOLD + "Prefix: " + ChatColor.RESET + plugin.getNoticePrefix().replace("&", "§"));
				sender.sendMessage(ChatColor.GOLD + "Message: " + ChatColor.RESET + NoticeTask.lastMessage + "/" + plugin.numberOfMessages());
				sender.sendMessage(ChatColor.GOLD + "Interval: " + ChatColor.WHITE + plugin.getNoticeInterval());
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice list
	public boolean onListCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			int page;
			if(plugin.numberOfMessages() <= 0) {
				sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.RED + "There are no messages.");
			} else {
				if(args.length == 1) {
					page = 1;
				} else if(args.length == 2) {
					try {
						page = Integer.parseInt(args[1]);
					} catch(NumberFormatException e) {
						return false;
					}
					if(page <= 0 || page > (int) Math.ceil((double) plugin.numberOfMessages() / 8)) {
						sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.RED + "Page number is out of range.");
						return true;
					}
				} else {
					return false;
				}
				sender.sendMessage(ChatColor.AQUA + String.format("§l=== Notice Messages [Page %d/%d] ===", page, (int) Math.ceil((double) plugin.numberOfMessages() / 8)));
				int startIndex = Math.abs(page - 1) * 8;
				int stopIndex = Math.min(page * 8, plugin.numberOfMessages());
				for(int index = startIndex + 1; index <= stopIndex; index++) {
					sender.sendMessage(String.format("§6§l%d. §r%s", index, plugin.getNoticeMessage(index).replace("&", "§")));
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice add <message>
	public boolean onAddCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length > 1) {
				StringBuilder message = new StringBuilder();

				for(int index = 1; index < args.length; index++) {
					message.append(args[index]);
					message.append(" ");
				}
				message.deleteCharAt(message.lastIndexOf(" "));
				plugin.addNoticeMessage(message.toString());
				sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Added message successfully.");
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice addto <idx> <message>
	public boolean onAddToCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length > 2) {
				try {
					int index = Integer.parseInt(args[1]);
					if(index > 0 && index <= plugin.numberOfMessages() + 1) {
						StringBuilder message = new StringBuilder();

						for(int i = 2; i < args.length; i++) {
							message.append(args[i]);
							message.append(" ");
						}
						message.deleteCharAt(message.lastIndexOf(" "));
						plugin.addToNoticeMessage(message.toString(), index - 1);
						sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Added message to the index successfully.");
					} else {
						sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.RED + "Index is out of range.");
					}
				} catch(NumberFormatException e) {
					return false;
				}
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice delete <index>, /notice delete all
	public boolean onDeleteCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length != 2) {
				return false;
			} else if(plugin.numberOfMessages() > 0) {
				if(args[1].equalsIgnoreCase("all")) {
					plugin.removeMessages();
					sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Deleted all messages.");
				} else {
					try {
						int index = Integer.parseInt(args[1]);
						if(index > 0 && index <= plugin.numberOfMessages()) {
							sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Deleted message: '" + ChatColor.RESET + plugin.getNoticeMessage(index).replace("&", "§") + ChatColor.GREEN + "'");
							plugin.removeMessage(index);
						} else {
							sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.RED + "Index is out of range.");
						}
					} catch(NumberFormatException e) {
						return false;
					}
				}
			} else {
				sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.RED + "There are no messages to delete.");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice <on|off>
	public boolean onEnableCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("on")) {
					plugin.setNoticeEnabled(true);
					sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Notice is enabled.");
				} else if(args[0].equalsIgnoreCase("off")) {
					plugin.setNoticeEnabled(false);
					sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Notice is disabled.");
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice shuffle <on|off>
	public boolean onShuffleCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length == 2) {
				if(args[1].equalsIgnoreCase("on")) {
					plugin.setShuffle(true);
					sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Shuffle mode is enabled.");
				} else if(args[1].equalsIgnoreCase("off")) {
					plugin.setShuffle(false);
					sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Shuffle mode is disabled.");
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice prefix [prefix]
	public boolean onPrefixCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length > 1) {
				StringBuilder prefix = new StringBuilder();

				for(int i = 1; i < args.length; i++) {
					prefix.append(args[i]);
					prefix.append(" ");
				}
				prefix.deleteCharAt(prefix.lastIndexOf(" "));
				plugin.setNoticePrefix(prefix.toString());
				sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Set prefix successfully.");
			} else {
				plugin.setNoticePrefix("");
				sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Removed prefix successfully.");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice interval <interval>
	public boolean onIntervalCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length == 2) {
				try {
					long interval = Integer.parseInt(args[1]);
					if(interval <= 0) {
						sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.RED + "Interval must be greater than 0.");
					} else {
						plugin.setNoticeInterval(interval);
						sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Set interval of scheduled notice.");
					}
				} catch(NumberFormatException e) {
					return false;
				}
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice broadcast [index]
	public boolean onBroadcastCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(plugin.numberOfMessages() > 0) {
				if(args.length == 2) {
					try {
						int index = Integer.parseInt(args[1]);
						if(index > 0 && index <= plugin.numberOfMessages()) {
							plugin.notice(index);
						} else {
							sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.RED + "Index is out of range.");
						}
					} catch(NumberFormatException e) {
						return false;
					}
				} else if (args.length == 1) {
					plugin.notice(++NoticeTask.lastMessage);
				} else {
					return false;
				}
			} else {
				sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.RED + "There are no message to broadcast.");
			}
			return true;
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// /notice say <message>
	public boolean onSayCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length > 1) {
				StringBuilder message = new StringBuilder();

				for(int i = 1; i < args.length; i++) {
					message.append(args[i]);
					message.append(" ");
				}
				message.deleteCharAt(message.lastIndexOf(" "));
				plugin.notice(message.toString());
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}

	// notice reload
	public boolean onReloadCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("notice.admin")) {
			if(args.length == 1) {
				plugin.reloadConfiguration();
				sender.sendMessage(ChatColor.AQUA + "[NOTICE]" + ChatColor.GREEN + "Configuration reloaded.");
			} else {
				return false;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to perform this command.");
			plugin.logger.info(ChatColor.RED + sender.getName() + " doesn't have permission to perform that command.");
		}
		return true;
	}
}
