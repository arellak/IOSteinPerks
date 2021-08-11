package de.nimble.iostein.perks;

import de.nimble.iostein.PerksPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds all the neccessary info about a player and his perks
 */
public class PerkPlayer {

    private Player player;
    private int perkCount;
    private List<Perk> perks;

    public PerkPlayer(String playerName) {
        this.perks = new ArrayList<>();
        this.player = Bukkit.getPlayer(playerName);
        this.perkCount = 0;
    }

    /**
     * @param perk The perk that should be added to the player
     * @return Success/Error message
     */
    public String addPerk(Perk perk) {
        if(perkCount+1 <= PerksPlugin.perkConfig.getInt("general.perkLimit")) {
            for(Perk pk : perks) {
                if(pk.getName().equalsIgnoreCase(perk.getName())) {
                    return PerksPlugin.perkConfig.getString("general.perkActiveMessage");
                }
            }

            perks.add(perk);
            perkCount++;
            return PerksPlugin.perkConfig.getString("general.perkAddedMessage")
                    .replaceAll("%perkName%", perk.getName());
        } else {
            return PerksPlugin.perkConfig.getString("general.perkLimitMessage");
        }
    }

    /**
     *
     * @param perk The perk that should be removed from the player
     * @return String with a success message
     */
    public String removePerk(Perk perk) {
        if(perkCount == 0) {
            return "";
        }

        for(Perk pk : perks) {
            if(pk.getType() == perk.getType()) {
                perks.remove(pk);
                perkCount--;

                switch(perk.getType()) {
                    case SPEED:
                        player.removePotionEffect(PotionEffectType.SPEED);
                        break;
                    case STRENGTH:
                        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                        break;
                    case UNDERWATER_BREATHING:
                        player.removePotionEffect(PotionEffectType.WATER_BREATHING);
                        break;
                    case NO_GRAVITY:
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        break;
                    default:
                        break;
                }

                return PerksPlugin.perkConfig.getString("general.perkRemovedMessage")
                        .replaceAll("%perkName%", perk.getName());
            }
        }

        return "";
    }


    public List<Perk> getPerks() {
        return this.perks;
    }

    public int getPerkCount() {
        return this.perkCount;
    }

    public Player getPlayer() {
        return this.player;
    }

}
