package de.nimble.iostein.perks.npc;

import de.nimble.iostein.ItemPair;
import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkPlayer;
import de.nimble.iostein.perks.PerkPlayerManager;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.types.PerkManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class PerkInventory {

    private String displayName;
    private int size;
    private Inventory inventory;

    private int currentPage;

    public PerkInventory() {
        displayName = PerksPlugin.inventoryConfig.getInventoryName();
        size = PerksPlugin.inventoryConfig.getSize();

        inventory = Bukkit.createInventory(null, size, displayName);
    }

    public PerkInventory(InventoryView view) {
        this();

        this.inventory = view.getTopInventory();

        this.displayName = view.getTitle();
        this.size = view.getTopInventory().getSize();
    }

    /**
     * init a blank page
     */
    private void initBlankPage() {
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, ItemCreator.create(Material.BLACK_STAINED_GLASS_PANE, ""));
        }
    }

    /**
     * sets the items of the first page
     * @param playerUUID
     */
    public void loadContentFirstPage(UUID playerUUID) {
        // first step is to initialize each slot and afterwards overwrite the needed ones
        initBlankPage();

        for (ItemPair itemPair : PerksPlugin.inventoryConfig.getItems("firstPage", playerUUID)) {
            inventory.setItem(itemPair.first, itemPair.second);
        }

        replaceLockedPerks(playerUUID);
        replaceButtons(playerUUID);
    }

    /**
     * sets the items of the second page
     * @param playerUUID
     */
    public void loadContentSecondPage(UUID playerUUID) {
        initBlankPage();

        for (ItemPair itemPair : PerksPlugin.inventoryConfig.getItems("secondPage", playerUUID)) {
            inventory.setItem(itemPair.first, itemPair.second);
        }

        replaceLockedPerks(playerUUID);
        replaceButtons(playerUUID);
    }

    /**
     * Replaces inactive buttons with active buttons when User has active perks
     * @param playerUUID Unique ID of the player that has caused this method to be called
     */
    public void replaceButtons(UUID playerUUID) {
        List<PerkType> activePerks = PerksPlugin.perkStates.getActivePerks(playerUUID);

        Material activeButtonMaterial = PerksPlugin.inventoryConfig.getActiveButtonMaterial();
        String activeDisplayName = PerksPlugin.inventoryConfig.getActiveButtonName();

        // for explanation look in replaceLockedPerks because its the same procedure
        for (PerkType activePerk : activePerks) {
            PerkItem perkItem = PerkManager.getInstance().getPerkByType(activePerk).getPerkItem();
            String perkItemName = perkItem.getDisplayName().replaceAll("§", "&");

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                String itemName = item.getItemMeta().getDisplayName().replaceAll("§", "&");

                if (perkItemName.equalsIgnoreCase(itemName)) {
                    inventory.setItem(i + 9, ItemCreator.create(activeButtonMaterial, activeDisplayName));
                }
            }
        }
    }

    /**
     * Replaces inactive buttons with locked items when User has locked Perks and replaces locked buttons with inactive buttons
     * in case user has unlocked perks
     * @param playerUUID Unique ID of the player that has caused this method to be called
     */
    public void replaceLockedPerks(UUID playerUUID) {
        // get all unlocked perks
        List<PerkType> unlockedPerks = PerksPlugin.perkStates.getUnlockedPerks(playerUUID);

        // get the material for a locked item
        Material lockMaterial = Material.getMaterial(PerksPlugin.inventoryConfig.getString("inventory.content.secondPage.notUnlockedBarriers.type"));
        // get the displayName for a locked item
        String lockDisplayName = PerksPlugin.inventoryConfig.getString("inventory.content.secondPage.notUnlockedBarriers.displayName");

        // get all perk types
        PerkType[] types = PerkType.values();

        for (PerkType type : types) {
            // nothing should happen when PerkType is NONE
            if (type == PerkType.NONE) continue;

            // determinethe perkItem by its type
            PerkItem perkItem = PerkManager.getInstance().getPerkByType(type).getPerkItem();
            // save itemName in a variable and replace § with & otherwise it can cause errors
            String perkItemName = perkItem.getDisplayName().replaceAll("§", "&");

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                String itemName = item.getItemMeta().getDisplayName().replaceAll("§", "&");

                // if perkItemName equals itemName replace the Item at i+9 with lock button
                // i+9 => slot for button
                if (perkItemName.equalsIgnoreCase(itemName)) {
                    inventory.setItem(i + 9, ItemCreator.create(lockMaterial, lockDisplayName));
                }
            }
        }


        // same thing as above except the materials are different
        Material inactiveButtonMaterial = PerksPlugin.inventoryConfig.getInactiveButtonMaterial();
        String inactiveDisplayName = PerksPlugin.inventoryConfig.getInactiveButtonName();

        for (PerkType unlockedPerk : unlockedPerks) {
            PerkItem perkItem = PerkManager.getInstance().getPerkByType(unlockedPerk).getPerkItem();
            String perkItemName = perkItem.getDisplayName().replaceAll("§", "&");

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                String itemName = item.getItemMeta().getDisplayName().replaceAll("§", "&");

                if (perkItemName.equalsIgnoreCase(itemName)) {
                    inventory.setItem(i + 9, ItemCreator.create(inactiveButtonMaterial, inactiveDisplayName));
                }
            }
        }
    }

    /**
     * Opens NPC inventory
     * @param player the player that has clicked the npc
     */
    public void open(Player player) {
        loadContentFirstPage(player.getUniqueId());
        player.openInventory(this.inventory);
        player.updateInventory();
    }

    /**
     * closes NPC inventory and saves the current state in a config
     */
    public void close() {
        for (PerkPlayer player : PerkPlayerManager.getInstance().getPerkPlayers()) {
            List<Perk> perks = player.getPerks();

            PerksPlugin.perkStates.savePerkState(player.getPlayer().getUniqueId().toString(), perks);
        }
    }

    /**
     * Determines what happens when user clicks on the leftPage Button
     * @param playerUUID
     */
    public void onLeftPageClick(UUID playerUUID) {
        currentPage--;
        loadContentFirstPage(playerUUID);
    }

    /**
     * Determines what happens when user clicks on the rightPage Button
     * @param playerUUID
     */
    public void onRightPageClick(UUID playerUUID) {
        currentPage++;
        loadContentSecondPage(playerUUID);
    }

    /**
     * Determines what happens on a click
     * @param item
     * @param playerUUID
     */
    public void handlePageClick(ItemStack item, UUID playerUUID) {
        String displayName = item.getItemMeta().getDisplayName().replaceAll("§", "&");

        String firstPageName = PerksPlugin.inventoryConfig.getString("inventory.content.firstPage.nextPage.displayName")
                .replaceAll("§", "&");
        String secondPageName = PerksPlugin.inventoryConfig.getString("inventory.content.secondPage.lastPage.displayName")
                .replaceAll("§", "&");

        if (displayName.equalsIgnoreCase(firstPageName)) {
            onRightPageClick(playerUUID);
        } else if (displayName.equalsIgnoreCase(secondPageName)) {
            onLeftPageClick(playerUUID);
        }
    }

    /*
    Just some getters and setters
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
