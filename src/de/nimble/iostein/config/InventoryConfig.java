package de.nimble.iostein.config;

import com.mojang.datafixers.util.Pair;
import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.npc.ItemCreator;
import de.nimble.iostein.perks.npc.PerkItem;
import de.nimble.iostein.perks.types.PerkManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryConfig {

    private String fileName;
    private File file;
    private YamlConfiguration configuration;

    public InventoryConfig() {
        this.fileName = "inventoryConfig";
        this.file = new File("plugins/iosteinPerks/" + this.fileName + ".yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public List<Pair<Integer, ItemStack>> getFirstPageItems() {
        List<Pair<Integer, ItemStack>> items = new ArrayList<>();
        /*
        for(String key : configuration.getConfigurationSection("inventory.content.pages").getKeys(true)) {
            if(key.contains("count")) {

            }
        }
        */
        String fullKeyPath = "inventory.content.firstPage";
        for(String key : configuration.getConfigurationSection(fullKeyPath).getKeys(false)) {
            if(key.equalsIgnoreCase("button")) {
                List<Integer> slots = configuration.getIntegerList(fullKeyPath + "." + key + ".slots");
                Material type = Material.getMaterial(getString(fullKeyPath + "." + key + ".inactive.type"));
                String displayName = getString(fullKeyPath + "." + key + ".inactive.displayName");
                ItemStack item = ItemCreator.create(type, displayName);

                for(int slot : slots) {
                    items.add(new Pair<>(slot, item));
                }

                continue;
            }

            int slot = configuration.getInt(fullKeyPath + "." + key + ".slot");

            if(key.equalsIgnoreCase("instructions") || key.equalsIgnoreCase("nextPage")) {
                Material type = Material.getMaterial(getString(fullKeyPath + "." + key + ".type"));
                String displayName = getString(fullKeyPath + "." + key + ".displayName");
                ItemStack item = ItemCreator.create(type, displayName);

                items.add(new Pair<>(slot, item));
                continue;
            }

            PerkType type = PerkType.getTypeByName(getString(fullKeyPath + "." + key + ".perkType"));
            if(type == null) continue;

            ItemStack item = PerkManager.getInstance().getPerkByType(type).getPerkItem().getItem();
            if(item == null) continue;
            items.add(new Pair<>(slot, item));
        }

        return items;
    }

    public List<Pair<Integer, ItemStack>> getSecondPageItems() {
        List<Pair<Integer, ItemStack>> items = new ArrayList<>();

        String fullKeyPath = "inventory.content.secondPage";
        for(String key : configuration.getConfigurationSection(fullKeyPath).getKeys(false)) {
            if(key.equalsIgnoreCase("notUnlockedBarriers")
                    || key.equalsIgnoreCase("notUnlockedHeads")
                    || key.equalsIgnoreCase("button")) {
                List<Integer> slots = configuration.getIntegerList(fullKeyPath + "." + key + ".slots");
                Material type = null;
                String displayName = null;

                if(key.equalsIgnoreCase("button")) {
                    type = Material.getMaterial(getString(fullKeyPath + "." + key + ".inactive.type"));
                    displayName = getString(fullKeyPath + "." + key + ".inactive.displayName");
                } else {
                    type = Material.getMaterial(getString(fullKeyPath + "." + key + ".type"));
                    displayName = getString(fullKeyPath + "." + key + ".displayName");
                }


                ItemStack item = ItemCreator.create(type, displayName);

                for(int slot : slots) {
                    items.add(new Pair<>(slot, item));
                }

                continue;
            }

            int slot = configuration.getInt(fullKeyPath + "." + key + ".slot");

            if(key.equalsIgnoreCase("lastPage")) {
                Material type = Material.getMaterial(getString(fullKeyPath + "." + key + ".type"));
                String displayName = getString(fullKeyPath + "." + key + ".displayName");
                ItemStack item = ItemCreator.create(type, displayName);

                items.add(new Pair<>(slot, item));
                continue;
            }

            PerkType type = PerkType.getTypeByName(getString(fullKeyPath + "." + key + ".perkType"));
            if(type == null) continue;

            ItemStack item = PerkManager.getInstance().getPerkByType(type).getPerkItem().getItem();
            if(item == null) continue;
            items.add(new Pair<>(slot, item));
        }

        return items;
    }


    public String getString(String name) {
        return ChatColor.translateAlternateColorCodes('&', configuration.getString(name));
    }

}
