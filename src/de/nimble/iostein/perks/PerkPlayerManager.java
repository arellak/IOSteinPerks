package de.nimble.iostein.perks;

import de.nimble.iostein.PerksPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PerkPlayerManager {

    private List<PerkPlayer> perkPlayers;

    private static PerkPlayerManager instance;

    /**
     * We only want one instance of this Manager
     *
     * @return instance of the Manager
     */
    public static PerkPlayerManager getInstance() {
        if (instance == null) {
            instance = new PerkPlayerManager();
        }
        return instance;
    }

    private PerkPlayerManager() {
        this.perkPlayers = new ArrayList<>();
    }

    /**
     * calls all onAction methods of the active perks of a player
     */
    public void action() {
        for (PerkPlayer perkPlayer : perkPlayers) {
            for (Perk perk : perkPlayer.getPerks()) {
                perk.onAction(perkPlayer.getPlayer());
            }
        }
    }

    /**
     * @param player The player that should get the perk
     * @param perk   The perk that should be added to the given Player
     */
    public String addPerk(Player player, Perk perk) {
        PerkPlayer perkPlayer = new PerkPlayer(player);

        // if player is in
        for (PerkPlayer pp : perkPlayers) {
            if (pp.getPlayer().getUniqueId().compareTo(player.getUniqueId()) == 0) {
                perk.onAction(player);
                return pp.addPerk(perk);
            }
        }


        // add player to list if he isn't already in it
        String msg = perkPlayer.addPerk(perk);
        perkPlayers.add(perkPlayer);
        perk.onAction(player);

        return msg;
    }

    /**
     * @param player The player that should get the perk
     * @param perk   The perk that should be removed from the given Player
     * @return Success message
     */
    public String removePerk(Player player, Perk perk) {
        for (PerkPlayer perkPlayer : perkPlayers) {
            if (perkPlayer.getPlayer().getDisplayName().equalsIgnoreCase(player.getDisplayName())) {
                return perkPlayer.removePerk(perk);
            }
        }

        return PerksPlugin.generalConfig.getPerkNotActiveMessage().replaceAll("%perk%", perk.getName());
    }

    /**
     * @return All active PerkPlayers
     */
    public List<PerkPlayer> getPerkPlayers() {
        return this.perkPlayers;
    }

    /**
     * @param uuid of the player you want to get
     * @return <b>PerkPlayer</b> by the given UUID or <b>NULL</b>
     */
    public PerkPlayer getPerkPlayerByUUID(UUID uuid) {
        for (PerkPlayer perkPlayer : perkPlayers) {
            if (perkPlayer.getPlayer().getUniqueId().compareTo(uuid) == 0) {
                return perkPlayer;
            }
        }

        return null;
    }

}
