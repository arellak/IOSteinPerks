package de.nimble.iostein.config;

import de.nimble.iostein.perks.PerkType;
import org.bukkit.Material;

public class PerkConfig extends BaseConfig {

    /**
     * Initalizes all the needed components for reading the Perks from a file
     */
    public PerkConfig() {
        super("perks");
    }

    public int getId(String name) {
        return configuration.getInt(name + ".id");
    }

    public String getDisplayName(String name) {
        String displayName = getString(name + ".displayName");
        if(displayName == null || displayName.isEmpty()) {
            displayName = "&8Default name";
        }
        return displayName;
    }

    public String getDescription(String name) {
        String description = getString(name + ".description");
        if(description == null || description.isEmpty()) {
            description = "&8Default description";
        }
        return description;
    }

    /**
     * Checks if a given PerkType is given and if so it reads the value from the config, otherwise it just sets the strength to -1
     * @param name The perks name
     * @return The value of the strength. Returns -1 if Perk doesn't need a strength
     */
    public int getStrength(String name) {
        int strength = -1;

        PerkType perkType = getType(name);
        if(perkType == PerkType.SPEED || perkType == PerkType.STRENGTH) {
            strength = configuration.getInt(name + ".strength");
        }

        return strength;
    }

    /**
     * @param name The perks name
     * @return either <b>NONE</b> or the PerkType given in the config
     */
    public PerkType getType(String name) {
        PerkType type = PerkType.getTypeByName(getString(name + ".type"));
        if(type == null) {
            type = PerkType.NONE;
        }

        return type;
    }

    public Material getItem(String name) {
        return Material.getMaterial(getString(name + ".item"));
    }

}