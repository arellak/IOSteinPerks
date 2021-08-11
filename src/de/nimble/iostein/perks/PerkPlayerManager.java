package de.nimble.iostein.perks;

import java.util.ArrayList;
import java.util.List;

public class PerkPlayerManager {

    private List<PerkPlayer> perkPlayers;

    private static PerkPlayerManager instance;

    /**
     * We only want one instance of this Manager
     * @return instance of the Manager
     */
    public static PerkPlayerManager getInstance() {
        if(instance == null) {
            instance = new PerkPlayerManager();
        }
        return instance;
    }

    private PerkPlayerManager() {
        this.perkPlayers = new ArrayList<>();
    }

    public void action() {
        for (PerkPlayer perkPlayer : perkPlayers) {
            for (Perk perk : perkPlayer.getPerks()) {
                perk.onAction(perkPlayer.getPlayer());
            }
        }
    }

    /**
     * @param playerName The player that should get the perk
     * @param perk The perk that should be added to the given Player
     */
    public String addPerk(String playerName, Perk perk) {
        for(PerkPlayer perkPlayer : perkPlayers) {
            if(perkPlayer.getPlayer().getDisplayName().equalsIgnoreCase(playerName)) {
                return perkPlayer.addPerk(perk);
            }
        }

        PerkPlayer perkPlayer = new PerkPlayer(playerName);
        String msg = perkPlayer.addPerk(perk);
        perkPlayers.add(perkPlayer);
        return msg;
    }

    /**
     * @param playerName The player that should get the perk
     * @param perk The perk that should be removed from the given Player
     */
    public String removePerk(String playerName, Perk perk) {
        for(PerkPlayer perkPlayer : perkPlayers) {
            if(perkPlayer.getPlayer().getDisplayName().equalsIgnoreCase(playerName)) {
                return perkPlayer.removePerk(perk);
            }
        }
        return "";
    }

    public List<PerkPlayer> getPerkPlayers() {
        return this.perkPlayers;
    }

    public PerkPlayer getPerkPlayerByName(String name) {
        for(PerkPlayer perkPlayer : perkPlayers) {
            if(perkPlayer.getPlayer().getDisplayName().equalsIgnoreCase(name)) {
                return perkPlayer;
            }
        }
        return null;
    }

}
