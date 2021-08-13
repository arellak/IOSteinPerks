package de.nimble.iostein.listener;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkPlayer;
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
        if (event.getCurrentItem() == null
                || event.getCurrentItem().getType() == Material.AIR) return;

        event.setCancelled(true);

        Player clicker = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        ItemStack perkItem = event.getInventory().getItem(event.getSlot() - 9);

        String clickedDisplayName = perkItem.getItemMeta().getDisplayName().replaceAll("§", "&");

        PerkInventory inventory = new PerkInventory(clicker.getOpenInventory());

        inventory.handlePageClick(currentItem, clicker.getUniqueId().toString());

        Material activeButtonMaterial = PerksPlugin.inventoryConfig.getActiveButtonMaterial();
        Material inactiveButtonMaterial = PerksPlugin.inventoryConfig.getInactiveButtonMaterial();
        Material notUnlockedType = PerksPlugin.inventoryConfig.getNotUnlockedType();
        Material instructionType = PerksPlugin.inventoryConfig.getInstructionType();
        PerkPlayer perkPlayer = PerkPlayerManager.getInstance().getPerkPlayerByUUID(clicker.getUniqueId().toString());

        if (currentItem.getType() == inactiveButtonMaterial) {
            if(perkPlayer != null && perkPlayer.getPerkCount() >= PerksPlugin.generalConfig.getPerkLimit()) {
                clicker.sendMessage(PerksPlugin.generalConfig.getPerkLimitMessage());
                return;
            }

            handle(true, clickedDisplayName, clicker);
            currentItem.setType(activeButtonMaterial);
        } else if (currentItem.getType() == activeButtonMaterial) {
            handle(false, clickedDisplayName, clicker);
            currentItem.setType(inactiveButtonMaterial);
        } else if (currentItem.getType() == notUnlockedType) {
            clicker.sendMessage(PerksPlugin.generalConfig.getPerkNotUnlockedMessage());
        } else if(currentItem.getType() == instructionType) {
            clicker.sendMessage(PerksPlugin.generalConfig.getPerkDefinitionMessage());
        }
    }

    private void handle(boolean add, String displayName, Player clicker) {
        for (Perk perk : PerkManager.getInstance().getPerks()) {
            String perkDisplayName = perk.getPerkItem().getDisplayName().replaceAll("§", "&");

            if (displayName.equalsIgnoreCase(perkDisplayName)) {
                String message = "";
                if (add) {
                    message = PerkPlayerManager.getInstance().addPerk(clicker, perk);
                } else {
                    message = PerkPlayerManager.getInstance().removePerk(clicker, perk);
                }

                clicker.sendMessage(message);
            }
        }
    }
}