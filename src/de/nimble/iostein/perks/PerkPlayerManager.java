package de.nimble.iostein.perks;

import de.nimble.iostein.PerksPlugin;
import org.bukkit.entity.Player;

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
     * @param player The player that should get the perk
     * @param perk The perk that should be added to the given Player
     */
    public String addPerk(Player player, Perk perk) {
        // TODO wenn viertes perk das ERSTE mal aktiviert wird, dann gehts noch durch, aber beim zweiten mal klicken nicht mehr
        for(PerkPlayer perkPlayer : perkPlayers) {
            if(perkPlayer.getPlayer().getDisplayName().equalsIgnoreCase(player.getDisplayName())) {
                if(perkLimitReached(perkPlayer.getPerkCount())) {
                    return PerksPlugin.generalConfig.getPerkLimitMessage();
                }

                PerksPlugin.perkStates.savePerkState(player.getUniqueId().toString(), perk.getType());
                PerkPlayerManager.getInstance().action();
                return perkPlayer.addPerk(perk);
            }
        }

        PerkPlayer perkPlayer = new PerkPlayer(player);

        if(perkLimitReached(perkPlayer.getPerkCount())) {
            return PerksPlugin.generalConfig.getPerkLimitMessage();
        }

        String msg = perkPlayer.addPerk(perk);
        perkPlayers.add(perkPlayer);
        PerksPlugin.perkStates.savePerkState(player.getUniqueId().toString(), perk.getType());

        return msg;
    }

    public boolean perkLimitReached(int perkCount) {
        return perkCount > PerksPlugin.generalConfig.getPerkLimit();
    }

    /**
     * @param player The player that should get the perk
     * @param perk The perk that should be removed from the given Player
     */
    public String removePerk(Player player, Perk perk) {
        for(PerkPlayer perkPlayer : perkPlayers) {
            if(perkPlayer.getPlayer().getDisplayName().equalsIgnoreCase(player.getDisplayName())) {
                PerksPlugin.perkStates.removePerkState(player.getUniqueId().toString(), perk.getType());
                return perkPlayer.removePerk(perk);
            }
        }

        return PerksPlugin.generalConfig.getPerkNotActiveMessage().replaceAll("%perk%", perk.getName());
    }

    public List<PerkPlayer> getPerkPlayers() {
        return this.perkPlayers;
    }

    public PerkPlayer getPerkPlayerByUUID(String uuid) {
        for(PerkPlayer perkPlayer : perkPlayers) {
            if(perkPlayer.getPlayer().getUniqueId().toString().equalsIgnoreCase(uuid)) {
                return perkPlayer;
            }
        }

        return null;
    }

}
