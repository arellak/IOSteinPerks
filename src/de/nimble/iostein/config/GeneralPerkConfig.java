package de.nimble.iostein.config;

import org.bukkit.Material;

/**
 * This class is mostly for reading all the messages from the config used ingame like error messages
 */
public class GeneralPerkConfig extends BaseConfig {

    /**
     * Initalizes all the needed components for reading the Perks from a file
     * */
    public GeneralPerkConfig() {
        super("config");
    }

    /**
     * @return the perk limit
     */
    public int getPerkLimit() {
        return getInt("messages.perkLimit");
    }

    /**
     * @return message when you have the maximum perks
     */
    public String getPerkLimitMessage() {
        return getString("messages.perkLimitMessage")
                .replaceAll("%prefix%", getPrefix())
                .replaceAll("%perkLimit%", String.valueOf(getPerkLimit()));
    }

    /**
     * @return message when you try to activate an already activated perk
     */
    public String getPerkActiveMessage() {
        return getString("messages.perkActiveMessage");
    }

    /**
     * @return message when you activated a perk
     */
    public String getPerkAddedMessage() {
        return getString("messages.perkAddedMessage");
    }

    /**
     * @return message when you deactivated a perk
     */
    public String getPerkRemovedMessage() {
        return getString("messages.perkRemovedMessage");
    }

    /**
     * @return citybuild prefix so I don't have to write it 9239829382 times on my own
     */
    public String getPrefix() {
        return getString("messages.prefix");
    }

    /**
     * @return Message what Perks are, replaces %prefix% with the given prefix and %perkLimit% with the given perkLimit
     */
    public String getPerkDefinitionMessage() {
        return getString("messages.perkDefinition")
                .replaceAll("%prefix%", getPrefix())
                .replaceAll("%perkLimit%", String.valueOf(getPerkLimit()));
    }

    /**
     * @return message when a perk is not unlocked
     */
    public String getPerkNotUnlockedMessage() {
        return getString("messages.perkNotUnlocked");
    }

    /**
     * @return message when you have no perks unlocked yet
     */
    public String getNoPerksUnlockedMessage() {
        return getString("messages.noPerksUnlocked").replaceAll("%prefix%", getPrefix());
    }

    /**
     * @return message when a perk is not active
     */
    public String getPerkNotActiveMessage() {
        return getString("messages.perkNotActive");
    }

    /**
     * @return message when you spawn a perk npc
     */
    public String getPerkVillagerSpawnedMessage() {
        return getString("messages.perkVillagerSpawned").replaceAll("%prefix%", getPrefix());
    }


    public int getInt(String name) {
        return configuration.getInt(name);
    }

}
