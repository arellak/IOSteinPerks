package de.nimble.iostein.config;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PerkStates extends BaseConfig{

    public PerkStates() {
        super("perkStates");
    }

    public void savePerkState(String playerUUID, List<Perk> perks) {
        List<String> activePerksList = new ArrayList<>();

        for(Perk p : perks) {
            activePerksList.add(p.getType().name());
        }

        configuration.set(playerUUID + ".activePerks", activePerksList);

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePerkState(String playerUUID, PerkType toRemove) {
        List<String> activePerksList = getActivePerkStrings(playerUUID);

        activePerksList.removeIf(perkString -> perkString.equalsIgnoreCase(toRemove.name()));

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
