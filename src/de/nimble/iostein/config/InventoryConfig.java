package de.nimble.iostein.config;

import de.nimble.iostein.ItemPair;
import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkPlayer;
import de.nimble.iostein.perks.PerkPlayerManager;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.npc.ItemCreator;
import de.nimble.iostein.perks.types.PerkManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryConfig extends BaseConfig {

    public InventoryConfig() {
        super("inventoryConfig");
    }

    /**
     * Reads from the config each item and its slot and type etc..<br>
     * Also checks what happens in case it is a special Item that needs extra care
     * @param page Defines which page should be read
     * @return ItemPairs specific to the page
     */
    public List<ItemPair> getItems(String page, UUID playerUUID) {
        List<ItemPair> items = new ArrayList<>();

        String fullKeyPath = "inventory.content." + page;

        for (String key : configuration.getConfigurationSection(fullKeyPath).getKeys(false)) {
            if (key.equalsIgnoreCase("button")
                    || key.equalsIgnoreCase("notUnlockedBarriers")
                    || key.equalsIgnoreCase("notUnlockedHeads")) {
                List<Integer> slots = configuration.getIntegerList(fullKeyPath + "." + key + ".slots");
                Material type = null;
                String displayName = null;

                if (key.equalsIgnoreCase("button")) {
                    type = Material.getMaterial(getString(fullKeyPath + "." + key + ".inactive.type"));
                    displayName = getString(fullKeyPath + "." + key + ".inactive.displayName").replaceAll("§", "&");
                } else {
                    type = Material.getMaterial(getString(fullKeyPath + "." + key + ".type"));
                    displayName = getString(fullKeyPath + "." + key + ".displayName");
                }

                ItemStack item = ItemCreator.create(type, displayName);

                for (int slot : slots) {
                    items.add(new ItemPair(slot, item));
                }

                continue;
            }

            int slot = configuration.getInt(fullKeyPath + "." + key + ".slot");


            if (key.equalsIgnoreCase("instructions")) {
                Material type = Material.getMaterial(getString(fullKeyPath + "." + key + ".type"));
                String displayName = getString(fullKeyPath + "." + key + ".displayName");
                ItemStack item = ItemCreator.create(type, displayName);

                items.add(new ItemPair(slot, item));
                continue;
            }

            if (key.equalsIgnoreCase("nextPage") || key.equalsIgnoreCase("lastPage")) {
                Material type = Material.getMaterial(getString(fullKeyPath + "." + key + ".type"));
                String displayName = getString(fullKeyPath + "." + key + ".displayName");
                String ownerName = getString(fullKeyPath + "." + key + ".skullOwner");

                items.add(new ItemPair(slot, getPageItem(displayName, ownerName, type)));
                continue;
            }

            PerkType type = PerkType.getTypeByName(getString(fullKeyPath + "." + key + ".perkType"));
            if (type == null) continue;

            ItemStack item = PerkManager.getInstance().getPerkByType(type).getPerkItem().getItem();
            if (item == null) continue;
            items.add(new ItemPair(slot, item));
        }

        return items;
    }

    /**
     *
     * @param displayName The name that should be displayed on hover
     * @param ownerName The name of the SkullOwner / SkinOwner
     * @param type The type of Material, in most cases a Skull/Player head because other items will probably cause an error at this point
     * @return ItemStack for the pages
     */
    public ItemStack getPageItem(String displayName, String ownerName, Material type) {
        ItemStack item = new ItemStack(type);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setDisplayName(displayName);
        skullMeta.setOwner(ownerName);
        item.setItemMeta(skullMeta);

        return item;
    }

    /**
     * @return Type of the item represents the button that shows a specific Perk is active
     */
    public Material getActiveButtonMaterial() {
        return Material.getMaterial(getString("inventory.content.firstPage.button.active.type"));
    }

    /**
     * @return Name of the button when a perk is active
     */
    public String getActiveButtonName() {
        return getString("inventory.content.firstPage.button.active.displayName");
    }

    /**
     * @return Type of the item represents the button that shows a specific Perk is inactive
     */
    public Material getInactiveButtonMaterial() {
        return Material.getMaterial(getString("inventory.content.firstPage.button.inactive.type"));
    }

    /**
     * @return Name of the button when a perk is inactive
     */
    public String getInactiveButtonName() {
        return getString("inventory.content.firstPage.button.inactive.displayName");
    }

    /**
     * @return Type of the item that represents <i>Not unlocked</i> perks
     */
    public Material getNotUnlockedType() {
        return Material.getMaterial(getString("inventory.content.secondPage.notUnlockedBarriers.type"));
    }

    /**
     *
     * @return Type of the Item that represents the instructions
     */
    public Material getInstructionType() {
        return Material.getMaterial(getString("inventory.content.firstPage.instructions.type"));
    }

    /**
     *
     * @return size of the Perk inventory
     */
    public int getSize() {
        return configuration.getInt("inventory.size");
    }

    /**
     * @return Name of the Perk inventory
     */
    public String getInventoryName() {
        return getString("inventory.name");
    }

}