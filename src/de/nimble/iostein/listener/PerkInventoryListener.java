package de.nimble.iostein.listener;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkPlayerManager;
import de.nimble.iostein.perks.npc.PerkInventory;
import de.nimble.iostein.perks.types.PerkManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class PerkInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getView().getTitle().equalsIgnoreCase("Perks"))) return;
        if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        Player clicker = (Player) event.getWhoClicked();

        PerkInventory inventory = new PerkInventory(clicker.getOpenInventory());

        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();
        Material currentItemType = currentItem.getType();
        int slot = event.getSlot();
        ItemStack perkItem = event.getInventory().getItem(slot - 9);
        String clickedDisplayName = perkItem.getItemMeta().getDisplayName().replaceAll("§", "&");


        inventory.handlePageClick(currentItem);

        if(currentItemType == Material.GRAY_DYE) {
            for (Perk perk : PerkManager.getInstance().getPerks()) {
                String perkDisplayName = perk.getPerkItem().getDisplayName().replaceAll("§", "&");

                if (clickedDisplayName.equalsIgnoreCase(perkDisplayName)) {
                    String message = PerkPlayerManager.getInstance().addPerk(clicker.getDisplayName(), perk);
                    clicker.sendMessage(message);
                    PerkPlayerManager.getInstance().action();

                    currentItem.setType(Material.GREEN_DYE);
                }
            }
        } else if(event.getCurrentItem().getType() == Material.GREEN_DYE) {
            for (Perk perk : PerkManager.getInstance().getPerks()) {
                String perkDisplayName = perk.getPerkItem().getDisplayName().replaceAll("§", "&");

                if (clickedDisplayName.equalsIgnoreCase(perkDisplayName)) {
                    String message = PerkPlayerManager.getInstance().removePerk(clicker.getDisplayName(), perk);
                    clicker.sendMessage(message);

                    currentItem.setType(Material.GRAY_DYE);
                }
            }
        } else if(event.getCurrentItem().getType() == Material.BARRIER) {
            clicker.sendMessage(PerksPlugin.generalConfig.getPerkNotUnlockedMessage());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getView().getTitle().equalsIgnoreCase("Perks")) {
            // save state in file
        }
    }

}