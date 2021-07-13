package us.mcfriendly.core.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import us.mcfriendly.core.managers.DataManager;
import us.mcfriendly.core.managers.SQLManager;
import us.mcfriendly.core.managers.Util;

public class JoinEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		SQLManager.initializeNewPlayer(p);

		int current = Bukkit.getOnlinePlayers().size();
		int max = DataManager.config.getInt("options.joinevent.max-players");

		if (current >= max) {
			if (!p.hasPermission(DataManager.config.getString("options.joinevent.permission"))) {
				p.kickPlayer(Util.color(DataManager.config.getString("options.joinevent.kick-message")));
			}
		}
	}
}
