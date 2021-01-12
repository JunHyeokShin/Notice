package notice;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;
import java.util.logging.Logger;

public class Notice extends JavaPlugin {
	protected List<String> noticeMessages;
	protected String noticePrefix;
	protected long noticeInterval;
	protected boolean enabled;
	protected boolean shuffle;
	protected Logger logger;
	private NoticeTask noticeTask = new NoticeTask(this);
	public BukkitScheduler scheduler;

	public Notice() {}

	@Override
	public void onEnable() {
		logger = getServer().getLogger();

		saveDefaultConfig();
		reloadConfiguration();

		scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, noticeTask, noticeInterval * 20L, noticeInterval * 20L);

		NoticeCommands noticeCommands = new NoticeCommands(this);
		getCommand("notice").setExecutor(noticeCommands);

		logger.info(String.format("%s is enabled!", getDescription().getFullName()));
	}

	@Override
	public void onDisable() {
		logger.info(String.format("%s is disabled!", getDescription().getFullName()));
	}

	public void notice() {
		noticeTask.run();
	}

	public void notice(int index) {
		notice(noticeMessages.get(index - 1));
	}

	public void notice(String line) {
		String loggerPrefix = noticePrefix.replace("&", "§");
		String loggerMessage = line.replace("&", "§");
		String sendToMessage = loggerMessage.replace("{$online}", Integer.toString(getServer().getOnlinePlayers().size()));
		sendToMessage = sendToMessage.replace("{$maxplayer}", Integer.toString(getServer().getMaxPlayers()));
		for (Player player : getServer().getOnlinePlayers()) {
			if (player.hasPermission("notice.receiver")) {
				String sendToMessage1 = sendToMessage.replace("{$player}", player.getName());
				sendToMessage1 = sendToMessage1.replace("{$health}", (int) player.getHealth() + "/" + (int) player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
				sendToMessage1 = sendToMessage1.replace("{$world}", player.getWorld().getName());
				sendToMessage1 = sendToMessage1.replace("{$location}", player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
				player.sendMessage(loggerPrefix + sendToMessage1);
			}
		}
		logger.info(loggerPrefix + loggerMessage);
	}

	public void addNoticeMessage(String message) {
		noticeMessages.add(message);
		saveConfiguration();
	}

	public void addToNoticeMessage(String message, int index) {
		noticeMessages.add(index - 1, message);
		saveConfiguration();
	}

	public String getNoticeMessage(int index) {
		return noticeMessages.get(index - 1);
	}

	public int numberOfMessages() {
		return noticeMessages.size();
	}

	public void removeMessages() {
		noticeMessages.clear();
		saveConfiguration();
	}

	public void removeMessage(int index) {
		noticeMessages.remove(index - 1);
		saveConfiguration();
	}

	public boolean isNoticeEnabled() {
		return enabled;
	}

	public void setNoticeEnabled(boolean enable) {
		this.enabled = enable;
		saveConfiguration();
	}

	public boolean isShuffle() {
		return shuffle;
	}

	public void setShuffle(boolean shuffle) {
		this.shuffle = shuffle;
		saveConfiguration();
	}

	public void saveConfiguration() {
		getConfig().set("messages", noticeMessages);
		getConfig().set("interval", noticeInterval);
		getConfig().set("prefix", noticePrefix);
		getConfig().set("enabled", enabled);
		getConfig().set("shuffle", shuffle);
		saveConfig();
	}

	public void reloadConfiguration() {
		reloadConfig();
		noticePrefix = getConfig().getString("prefix");
		noticeMessages = getConfig().getStringList("messages");
		noticeInterval = getConfig().getInt("interval", 60);
		enabled = getConfig().getBoolean("enabled", true);
		shuffle = getConfig().getBoolean("shuffle", false);
	}

	public String getNoticePrefix() {
		return noticePrefix;
	}

	public void setNoticePrefix(String noticePrefix) {
		this.noticePrefix = noticePrefix;
		saveConfiguration();
	}

	public long getNoticeInterval() {
		return noticeInterval;
	}

	public void setNoticeInterval(long noticeInterval) {
		this.noticeInterval = noticeInterval;
		saveConfiguration();
		scheduler.cancelTasks(this);
		scheduler.scheduleSyncRepeatingTask(this, noticeTask, noticeInterval * 20L, noticeInterval * 20L);
	}
}