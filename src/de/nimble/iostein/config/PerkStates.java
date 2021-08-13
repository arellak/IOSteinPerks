package de.nimble.iostein.config;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PerkStates extends BaseConfig{

    public PerkStates() {
        super("perkStates");
    }

    public void savePerkState(String playerUUID, List<Perk> perks) {
        List<String> activePerksList = new ArrayList<>();

        for(Perk p : perks) {
            if(activePerksList.contains(p.getType().name())) continue;
            activePerksList.add(p.getType().name());
        }

        configuration.set(playerUUID + ".activePerks", activePerksList);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unlockPerk(UUID playerUUID, PerkType perkType) {
        List<PerkType> unlockedTypes = getUnlockedPerks(playerUUID);
        List<String> unlockedTypeStrings = new ArrayList<>();
        unlockedTypes.forEach(unlockedType -> unlockedTypeStrings.add(unlockedType.name()));

        if(!(unlockedTypeStrings.contains(perkType.name()))) {
            unlockedTypeStrings.add(perkType.name());
        }

        configuration.set(playerUUID + ".unlockedPerks", unlockedTypeStrings);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param playerUUID
     * @return list with unlocked perks
     */
    public List<PerkType> getUnlockedPerks(UUID playerUUID) {
        List<PerkType> types = new ArrayList<>();

        for(String t : configuration.getStringList(playerUUID + ".unlockedPerks")) {
            PerkType perkType = PerkType.getTypeByName(t);
            types.add(perkType);
        }

        return types;
    }

    /**
     *
     * @param playerUUID
     * @return list with active perks but as strings
     */
    public List<String> getActivePerkStrings(UUID playerUUID) {
        return configuration.getStringList(playerUUID + ".activePerks");
    }

    /**
     *
     * @param playerUUID
     * @return list with active perks
     */
    public List<PerkType> getActivePerks(UUID playerUUID) {
        return getPerks(playerUUID, "activePerks");
    }

    /**
     * get all the active perks an user has
     * @param playerUUID
     * @param type
     * @return List with active perks
     */
    private List<PerkType> getPerks(UUID playerUUID, String type) {
        List<PerkType> activePerks = new ArrayList<>();
        List<String> activePerkStrings = configuration.getStringList(playerUUID + "." + type);

        activePerkStrings.forEach(
                perkString -> activePerks.add(PerkType.getTypeByName(perkString))
        );

        return activePerks;
    }

}
