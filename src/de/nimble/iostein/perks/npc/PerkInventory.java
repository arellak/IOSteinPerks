package de.nimble.iostein.perks.npc;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.PerkPlayerManager;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.types.PerkManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

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

    public void loadContentFirstPage() {
        // first step is to initialize each slot and afterwards overwrite the needed ones
        for(int i = 0; i < size; i++) {
            inventory.setItem(i, ItemCreator.create(Material.BLACK_STAINED_GLASS_PANE, "NOTHING'S HERE"));
        }

        inventory.setItem(2, PerkManager.getInstance().getPerkByType(PerkType.NO_FALL_DAMAGE).getPerkItem().create());
        inventory.setItem(5, PerkManager.getInstance().getPerkByType(PerkType.NO_FIRE_DAMAGE).getPerkItem().create());
        inventory.setItem(8, PerkManager.getInstance().getPerkByType(PerkType.NO_GRAVITY).getPerkItem().create());

        inventory.setItem(11, ItemCreator.create(Material.LIGHT_GRAY_DYE, PerksPlugin.generalConfig.getPerkDeactivatedMessage()));
        inventory.setItem(14, ItemCreator.create(Material.LIGHT_GRAY_DYE, PerksPlugin.generalConfig.getPerkDeactivatedMessage()));
        inventory.setItem(17, ItemCreator.create(Material.LIGHT_GRAY_DYE, PerksPlugin.generalConfig.getPerkDeactivatedMessage()));

        inventory.setItem(29, PerkManager.getInstance().getPerkByType(PerkType.UNDERWATER_BREATHING).getPerkItem().create());
        inventory.setItem(32, PerkManager.getInstance().getPerkByType(PerkType.SPEED).getPerkItem().create());
        inventory.setItem(35, PerkManager.getInstance().getPerkByType(PerkType.NO_HUNGER).getPerkItem().create());

        inventory.setItem(38, ItemCreator.create(Material.LIGHT_GRAY_DYE, PerksPlugin.generalConfig.getPerkDeactivatedMessage()));
        inventory.setItem(41, ItemCreator.create(Material.LIGHT_GRAY_DYE, PerksPlugin.generalConfig.getPerkDeactivatedMessage()));
        inventory.setItem(44, ItemCreator.create(Material.LIGHT_GRAY_DYE, PerksPlugin.generalConfig.getPerkDeactivatedMessage()));

        inventory.setItem(46, ItemCreator.create(Material.BOOK, PerksPlugin.generalConfig.getPerkInstructionBookMessage()));
        inventory.setItem(54, ItemCreator.create(Material.PLAYER_HEAD, PerksPlugin.generalConfig.getPerkPageMessage().replace("%page%", "2")));
    }

    public void loadContentSecondPage() {
        for(int i = 0; i < size; i++) {
            inventory.setItem(i, ItemCreator.create(Material.BLACK_STAINED_GLASS_PANE, "NOTHING'S HERE"));
        }


        inventory.setItem(2, PerkManager.getInstance().getPerkByType(PerkType.PREFIX).getPerkItem().create());
        inventory.setItem(5, PerkManager.getInstance().getPerkByType(PerkType.NO_FIRE_DAMAGE).getPerkItem().create());
        inventory.setItem(8, PerkManager.getInstance().getPerkByType(PerkType.NO_GRAVITY).getPerkItem().create());

        inventory.setItem(11, ItemCreator.create(Material.LIGHT_GRAY_DYE, PerksPlugin.generalConfig.getPerkDeactivatedMessage()));
        inventory.setItem(14, ItemCreator.create(Material.BARRIER, PerksPlugin.generalConfig.getPerkNotUnlockedMessage()));
        inventory.setItem(17, ItemCreator.create(Material.BARRIER, PerksPlugin.generalConfig.getPerkNotUnlockedMessage()));

        inventory.setItem(29, PerkManager.getInstance().getPerkByType(PerkType.STRENGTH).getPerkItem().create());
        inventory.setItem(32, PerkManager.getInstance().getPerkByType(PerkType.SPEED).getPerkItem().create());
        inventory.setItem(35, PerkManager.getInstance().getPerkByType(PerkType.NO_HUNGER).getPerkItem().create());

        inventory.setItem(38, ItemCreator.create(Material.LIGHT_GRAY_DYE, PerksPlugin.generalConfig.getPerkDeactivatedMessage()));
        inventory.setItem(41, ItemCreator.create(Material.BARRIER, PerksPlugin.generalConfig.getPerkNotUnlockedMessage()));
        inventory.setItem(44, ItemCreator.create(Material.BARRIER, PerksPlugin.generalConfig.getPerkNotUnlockedMessage()));

        inventory.setItem(46, ItemCreator.create(Material.PLAYER_HEAD, PerksPlugin.generalConfig.getPerkPageMessage().replace("%page%", "1")));
        inventory.setItem(54, ItemCreator.create(Material.PLAYER_HEAD, PerksPlugin.generalConfig.getPerkPageMessage().replace("%page%", "2")));
    }

    public void onLeftPageClick() {
        currentPage--;
        loadContentFirstPage();
    }

    public void onRightPageClick() {
        currentPage++;
        loadContentSecondPage();
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
