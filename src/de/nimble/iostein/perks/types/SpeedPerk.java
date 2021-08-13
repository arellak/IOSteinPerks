package de.nimble.iostein.perks.types;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedPerk extends Perk {

    public SpeedPerk() {
        init("speed", PerkType.SPEED);

    }

    @Override
    public void onAction(Player player) {
        System.out.println("onAction Speed!");
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, getStrength()));
    }

}
