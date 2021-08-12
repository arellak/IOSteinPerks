package de.nimble.iostein.config;

import de.nimble.iostein.perks.PerkType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PerkStates extends BaseConfig{

    public PerkStates() {
        super("perkStates");
    }

    public void savePerkState(String playerUUID, PerkType... activePerks) {
        List<String> activePerksList = getActivePerkStrings(playerUUID);

        for(PerkType perkType : activePerks) {
            if(activePerksList.contains(perkType.name())) continue;
            activePerksList.add(perkType.name());
        }

        configuration.set(playerUUID + ".activePerks", activePerksList);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePerkState(String playerUUID, PerkType... activePerks) {
        List<String> activePerksList = getActivePerkStrings(playerUUID);

        for(PerkType perkType : activePerks) {
            activePerksList.remove(perkType.name());
        }

        configuration.set(playerUUID + ".activePerks", activePerksList);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getActivePerkStrings(String playerUUID) {
        return configuration.getStringList(playerUUID + ".activePerks");
    }

    public List<PerkType> getActivePerks(String playerUUID) {
        List<PerkType> activePerks = new ArrayList<>();
        List<String> activePerkStrings = configuration.getStringList(playerUUID + ".activePerks");

        activePerkStrings.forEach(
                perkString -> activePerks.add(PerkType.getTypeByName(perkString))
        );

        return activePerks;
    }

}
