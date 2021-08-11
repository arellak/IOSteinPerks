package de.nimble.iostein.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PerkInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase("Perks")) {
            event.setCancelled(true);

            if(event.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) {

            }

        }
    }

}
