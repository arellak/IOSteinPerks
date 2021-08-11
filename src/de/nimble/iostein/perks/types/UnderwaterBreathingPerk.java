package de.nimble.iostein.perks.types;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.npc.PerkItem;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UnderwaterBreathingPerk extends Perk {

    public UnderwaterBreathingPerk() {
        setId(PerksPlugin.perkConfig.getId("underwaterBreathing"));
        setName(PerksPlugin.perkConfig.getDisplayName("underwaterBreathing"));
        setDescription(PerksPlugin.perkConfig.getDescription("underwaterBreathing"));
        setStrength(PerksPlugin.perkConfig.getStrength("underwaterBreathing"));
        setType(PerkType.UNDERWATER_BREATHING);
        setPerkItem(new PerkItem(getName(), PerksPlugin.perkConfig.getItem("underwaterBreathing")));
    }

    @Override
    public void onAction(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 10));
    }
}
