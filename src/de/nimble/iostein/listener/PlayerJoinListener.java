package de.nimble.iostein.listener;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkPlayer;
import de.nimble.iostein.perks.PerkPlayerManager;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.npc.NPC;
import de.nimble.iostein.perks.types.PerkManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Show Perk NPCs to every new player that joins
        for(NPC npc : PerksPlugin.npcManager.getRegisteredNPCs()) {
            npc.showTo(event.getPlayer());
        }

        // init playerperks
        PerkPlayer perkPlayer = new PerkPlayer(event.getPlayer());
        List<PerkType> perks = PerksPlugin.perkStates.getActivePerks(perkPlayer.getPlayer().getUniqueId());
        for(PerkType perkType : perks) {
            Perk perk = PerkManager.getInstance().getPerkByType(perkType);
            PerkPlayerManager.getInstance().addPerk(event.getPlayer(), perk);
        }
    }

}
