package de.nimble.iostein.listener;

import de.nimble.iostein.perks.npc.PerkInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getView().getTitle().equalsIgnoreCase("Perks"))) return;
        PerkInventory inventory = new PerkInventory(event.getView());
        inventory.close();
    }

}
