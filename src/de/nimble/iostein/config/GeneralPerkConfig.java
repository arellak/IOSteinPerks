package de.nimble.iostein.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class GeneralPerkConfig {

    private String configName;
    private File file;
    private YamlConfiguration configuration;

    /**
     * Initalizes all the needed components for reading the Perks from a file
     * */
    public GeneralPerkConfig() {
        this.configName = "config";
        this.file = new File("plugins/iosteinPerks/config.yml");
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public int getPerkLimit() {
        return getInt("messages.perkLimit");
    }

    public String getPerkLimitMessage() {
        return getString("messages.perkLimitMessage").replaceAll("%prefix%", getPrefix());
    }

    public String getPerkActiveMessage() {
        return getString("messages.perkActiveMessage");
    }

    public String getPerkAddedMessage() {
        return getString("messages.perkAddedMessage");
    }

    public String getPerkRemovedMessage() {
        return getString("messages.perkRemovedMessage");
    }

    public String getPrefix() {
        return getString("messages.prefix");
    }

    public String getPerkDefinitionMessage() {
        return getString("messages.perkDefinition").replaceAll("%prefix%", getPrefix());
    }

    public String getPerkDeactivatedMessage() {
        return getString("messages.perkDeactivated");
    }

    public String getPerkNotUnlockedMessage() {
        return getString("messages.perkNotUnlocked");
    }

    public String getPerkPageMessage() {
        return getString("messages.perkPage");
    }

    public String getPerkInstructionBookMessage() {
        return getString("messages.perkInstructionBook");
    }

    public String getPerkComingSoonMessage() {
        return getString("messages.perkComingSoon");
    }

    public String getNoPerksUnlockedMessage() {
        return getString("messages.noPerksUnlocked").replaceAll("%prefix%", getPrefix());
    }

    // NPC stuff
    public String getNPCName() {
        return getString("npc.name");
    }

    public String getSkinLink() {
        return getString("npc.skinLink");
    }

    public String getInventoryName() {
        return getString("npc.inventory.name");
    }

    public int getInventorySize() {
        return getInt("npc.inventory.size");
    }

    public Material getActivateButtonItem() {
        return Material.getMaterial(getString("npc.inventory.items.activateButton"));
    }

    public Material getNotUnlockedItem() {
        return Material.getMaterial(getString("npc.inventory.items.notUnlocked"));
    }

    public String getString(String name) {
        return ChatColor.translateAlternateColorCodes('&', configuration.getString(name));
    }

    public int getInt(String name) {
        return configuration.getInt(name);
    }

}
