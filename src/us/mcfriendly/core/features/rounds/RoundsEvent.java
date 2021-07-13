package us.mcfriendly.core.features.rounds;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import us.mcfriendly.core.managers.Util;

public class RoundsEvent implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().contains("§6§lRounds")) {
			e.setCancelled(true);
			if (!(e.getCurrentItem().getType() == Material.AIR)) {
				ItemStack a = e.getCurrentItem();
				String uuid = null;
				for (String lore : a.getItemMeta().getLore()) {
					if (lore.contains("UUID")) {
						uuid = lore.replace("§6UUID: §f", "");
					}
				}
				Player t = Bukkit.getPlayer(UUID.fromString(uuid));
				if (t == null) {
					p.sendMessage(Util.prefix() + "§cThat player is no longer online!");
					p.closeInventory();
					RoundsGui.roundsGui(p);
				} else {
					p.teleport(t);
					RoundsManager.resetPlayer(uuid, p);
				}
			}
		}
	}
}
