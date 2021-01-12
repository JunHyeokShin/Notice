package notice;

import java.util.Random;

public class NoticeTask implements Runnable {
	private final Random randomGenerator = new Random();
	private final Notice plugin;
	protected static int lastMessage = 0;

	public NoticeTask(Notice plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if (plugin.isNoticeEnabled() && plugin.getServer().getOnlinePlayers().size() != 0 && plugin.numberOfMessages() > 0) {
			if (plugin.isShuffle()) {
				lastMessage = Math.abs(randomGenerator.nextInt() % plugin.numberOfMessages());
			} else if (lastMessage >= plugin.numberOfMessages()) {
				lastMessage = 0;
			}

			if (lastMessage < plugin.numberOfMessages()) {
				plugin.notice(lastMessage + 1);
				lastMessage++;
			}
		}
	}
}
