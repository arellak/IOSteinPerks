package de.nimble.iostein.listener;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        for(NPC npc : PerksPlugin.npcManager.getRegisteredNPCs()) {
            npc.hideFrom(event.getPlayer());
        }
    }

}
