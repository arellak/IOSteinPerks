package de.nimble.iostein.perks.types;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UnderwaterBreathingPerk extends Perk {

    public UnderwaterBreathingPerk() {
        init("underwaterBreathing", PerkType.UNDERWATER_BREATHING);
    }

    @Override
    public void onAction(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 10));
    }
}
