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

    private void initBlankPage() {
        for(int i = 0; i < size; i++) {
            inventory.setItem(i, ItemCreator.create(Material.BLACK_STAINED_GLASS_PANE, ""));
        }
    }

    public void loadContentFirstPage(String playerUUID) {
        // first step is to initialize each slot and afterwards overwrite the needed ones
        initBlankPage();

        for(ItemPair itemPair : PerksPlugin.inventoryConfig.getItems("firstPage")) {
            inventory.setItem(itemPair.first, itemPair.second);
        }

        replaceButtons(playerUUID);
    }

    public void loadContentSecondPage(String playerUUID) {
        initBlankPage();

        for(ItemPair itemPair : PerksPlugin.inventoryConfig.getItems("secondPage")) {
            inventory.setItem(itemPair.first, itemPair.second);
        }

        replaceButtons(playerUUID);
    }

    public void replaceButtons(String playerUUID) {
        List<PerkType> activePerks = PerksPlugin.perkStates.getActivePerks(playerUUID);

        Material activeButtonMaterial = PerksPlugin.inventoryConfig.getActiveButtonMaterial();
        String activeDisplayName = PerksPlugin.inventoryConfig.getActiveButtonName();

        for(PerkType activePerk : activePerks) {
            PerkItem perkItem = PerkManager.getInstance().getPerkByType(activePerk).getPerkItem();
            String perkItemName = perkItem.getDisplayName().replaceAll("§", "&");

            for(int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                String itemName = item.getItemMeta().getDisplayName().replaceAll("§", "&");

                if(perkItemName.equalsIgnoreCase(itemName)) {
                    inventory.setItem(i+9, ItemCreator.create(activeButtonMaterial, activeDisplayName));
                }
            }
        }
    }

    public void open(Player player) {
        loadContentFirstPage(player.getUniqueId().toString());
        player.openInventory(this.inventory);
        player.updateInventory();
    }

    public void close() {
        for(PerkPlayer player : PerkPlayerManager.getInstance().getPerkPlayers()) {
            List<Perk> perks = player.getPerks();

            PerksPlugin.perkStates.savePerkState(player.getPlayer().getUniqueId().toString(), perks);
        }
    }

    public void onLeftPageClick(String playerUUID) {
        currentPage--;
        loadContentFirstPage(playerUUID);
    }

    public void onRightPageClick(String playerUUID) {
        currentPage++;
        loadContentSecondPage(playerUUID);
    }

    public void handlePageClick(ItemStack item, String playerUUID) {
        String displayName = item.getItemMeta().getDisplayName().replaceAll("§", "&");

        String firstPageName = PerksPlugin.inventoryConfig.getString("inventory.content.firstPage.nextPage.displayName")
                .replaceAll("§", "&");
        String secondPageName = PerksPlugin.inventoryConfig.getString("inventory.content.secondPage.lastPage.displayName")
                .replaceAll("§", "&");

        if(displayName.equalsIgnoreCase(firstPageName)) {
            onRightPageClick(playerUUID);
        } else if(displayName.equalsIgnoreCase(secondPageName)) {
            onLeftPageClick(playerUUID);
        }
    }

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
