package de.lcraft.test.spigot.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Bukkit.getConsoleSender().sendMessage("Default Message: " + e.getJoinMessage());
	}

}
