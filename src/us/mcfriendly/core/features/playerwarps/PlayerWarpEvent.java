package us.mcfriendly.core.features.playerwarps;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import us.mcfriendly.core.managers.Util;

public class PlayerWarpEvent implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(e.getView().getTitle().contains("§6§lPremium Confirmation")) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null)
				return;
			if (e.getCurrentItem().getType() == Material.GREEN_CONCRETE) {
				PlayerWarpManager.getPremium(p);
				p.closeInventory();
				return;
			}
			if (e.getCurrentItem().getType() == Material.RED_CONCRETE) {
				p.closeInventory();
				return;
			}
		}
		if (e.getView().getTitle().contains("§6§lPlayer Warps")) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null)
				return;
			if (e.getCurrentItem().getType() != Material.AIR) {
				int page = Integer
						.parseInt(ChatColor.stripColor(e.getView().getTitle()).replace("Player Warps | Page ", ""));
				if (e.getCurrentItem().getType() == Material.WHITE_BANNER
						&& e.getCurrentItem().getItemMeta().getDisplayName().contains("§6§lPrevious Page")) {
					PlayerWarpGui.openGui(p, page - 1);
					return;
				}
				if (e.getCurrentItem().getType() == Material.BLACK_BANNER
						&& e.getCurrentItem().getItemMeta().getDisplayName().contains("§6§lNext Page")) {
					PlayerWarpGui.openGui(p, page + 1);
					return;
				}
				if (e.getCurrentItem().getType() == Material.SUNFLOWER
						&& e.getCurrentItem().getItemMeta().getDisplayName().contains("§6§lRefresh")) {
					PlayerWarpGui.openGui(p, page);
					return;
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Premium Player Warp Spot") && e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
					p.sendMessage(Util.color(Util.prefix() + "&e&oTo purchase this spot, type /pwarp premium"));
					return;
				}
				String uuid = "";
				for (String lore : e.getCurrentItem().getItemMeta().getLore()) {
					if (lore.contains("UUID")) {
						uuid = ChatColor.stripColor(lore).split(":")[1].replace(" ", "");
					}
				}
				if (uuid == "") {
					p.sendMessage(Util.getMessage("options.playerwarps.messages.warpdoesnotexist"));
				} else {
					PlayerWarpManager.warp(p, uuid);
				}
			}
		}
	}
}
