package de.nimble.iostein.listener;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.npc.NPC;
import de.nimble.iostein.perks.npc.NPCClickAction;
import de.nimble.iostein.perks.npc.PerkInventory;
import de.nimble.iostein.perks.npc.events.NPCInteractionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class NPCClickListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(NPCInteractionEvent event) {
        NPC clicked = event.getClicked();

        if(clicked.getUuid().toString().equals(PerksPlugin.generalConfig.getString("npc.uuid"))) {
            if(event.getClickAction() == NPCClickAction.INTERACT_AT) {
                PerkInventory inventory = new PerkInventory();
                inventory.open(event.getPlayer());
            }
        }
    }

}
