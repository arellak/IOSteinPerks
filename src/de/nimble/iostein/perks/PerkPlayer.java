package de.nimble.iostein.perks;

import de.nimble.iostein.PerksPlugin;
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

    public PerkPlayer(Player player) {
        this.perks = new ArrayList<>();
        this.player = player;
        this.perkCount = 0;
    }

    /**
     * @param perk The perk that should be added to the player
     * @return Success/Error message
     */
    public String addPerk(Perk perk) {
        for (Perk pk : perks) {
            if (pk.getType() == perk.getType()) {
                return PerksPlugin.generalConfig.getPerkActiveMessage();
            }
        }

        perks.add(perk);
        perkCount++;
        return PerksPlugin.generalConfig.getPerkAddedMessage().replaceAll("%perkName%", perk.getName());
    }

    /**
     * Remove perk from a player
     * @param perk The perk that should be removed from the player
     * @return String with a success message
     */
    public String removePerk(Perk perk) {
        if (perkCount == 0) {
            return PerksPlugin.generalConfig.getPerkNotActiveMessage().replaceAll("%perk%", perk.getName());
        }

        for (Perk pk : perks) {
            if (pk.getType() == perk.getType()) {
                perks.remove(pk);
                perkCount--;

                switch (perk.getType()) {
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
                    case NO_FIRE_DAMAGE:
                        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                        break;
                    default:
                        break;
                }

                return PerksPlugin.generalConfig.getPerkRemovedMessage().replaceAll("%perkName%", perk.getName());
            }
        }

        return PerksPlugin.generalConfig.getPerkNotActiveMessage().replaceAll("%perk%", perk.getName());
    }

    // getter and setters
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
