package de.nimble.iostein.perks.types;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedPerk extends Perk {

    public SpeedPerk() {
        setId(PerksPlugin.perkConfig.getId("speed"));
        setName(PerksPlugin.perkConfig.getDisplayName("speed"));
        setDescription(PerksPlugin.perkConfig.getDescription("speed"));
        setStrength(PerksPlugin.perkConfig.getStrength("speed"));
        setType(PerkType.SPEED);
    }

    @Override
    public void onAction(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, getStrength()));
        System.out.println("Added potion effect to player...");
    }

}
