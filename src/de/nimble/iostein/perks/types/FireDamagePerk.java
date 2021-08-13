package de.nimble.iostein.perks.types;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FireDamagePerk extends Perk {

    public FireDamagePerk() {
        init("noFireDamage", PerkType.NO_FIRE_DAMAGE);

    }

    @Override
    public void onAction(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, getStrength()));
    }

}
