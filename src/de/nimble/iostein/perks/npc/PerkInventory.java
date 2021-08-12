package de.nimble.iostein.perks.npc;

import com.mojang.datafixers.util.Pair;
import de.nimble.iostein.PerksPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class PerkInventory {

    private String displayName;
    private int size;
    private Inventory inventory;

    private int currentPage;

    public PerkInventory() {
        displayName = PerksPlugin.generalConfig.getInventoryName();
        size = PerksPlugin.generalConfig.getInventorySize();
        inventory = Bukkit.createInventory(null, size, displayName);
        loadContentFirstPage();
    }

    public PerkInventory(InventoryView view) {
        this();
        this.inventory = view.getTopInventory();
        displayName = view.getTitle();
        size = view.getTopInventory().getSize();
    }

    private void initBlankPage() {
        for(int i = 0; i < size; i++) {
            inventory.setItem(i, ItemCreator.create(Material.BLACK_STAINED_GLASS_PANE, ""));
        }
    }

    public void loadContentFirstPage() {
        // first step is to initialize each slot and afterwards overwrite the needed ones
        initBlankPage();

        for(Pair<Integer, ItemStack> itemPair : PerksPlugin.inventoryConfig.getFirstPageItems()) {
            inventory.setItem(itemPair.getFirst(), itemPair.getSecond());
        }
    }

    public void loadContentSecondPage() {
        initBlankPage();

        for(Pair<Integer, ItemStack> itemPair : PerksPlugin.inventoryConfig.getSecondPageItems()) {
            inventory.setItem(itemPair.getFirst(), itemPair.getSecond());
        }
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
        player.updateInventory();
    }

    public void onLeftPageClick() {
        currentPage--;
        loadContentFirstPage();
    }

    public void onRightPageClick() {
        currentPage++;
        loadContentSecondPage();
    }

    public void handlePageClick(ItemStack item) {
        String displayName = item.getItemMeta().getDisplayName().replaceAll("§", "&");
        System.out.println("DisplayName: " + displayName.toLowerCase());

        String firstPageName = PerksPlugin.inventoryConfig.getString("inventory.content.firstPage.nextPage.displayName");
        String secondPageName = PerksPlugin.inventoryConfig.getString("inventory.content.secondPage.lastPage.displayName");

        if(displayName.equalsIgnoreCase(firstPageName)) {
            onLeftPageClick();
        } else if(displayName.equalsIgnoreCase(secondPageName)) {
            onRightPageClick();
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
