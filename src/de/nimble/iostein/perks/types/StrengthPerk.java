package de.nimble.iostein.perks.types;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.npc.PerkItem;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrengthPerk extends Perk {

    public StrengthPerk() {
        setId(PerksPlugin.perkConfig.getId("strength"));
        setName(PerksPlugin.perkConfig.getDisplayName("strength"));
        setDescription(PerksPlugin.perkConfig.getDescription("strength"));
        setStrength(PerksPlugin.perkConfig.getStrength("strength"));
        setType(PerkType.STRENGTH);
        setPerkItem(new PerkItem(getName(), PerksPlugin.perkConfig.getItem("strength")));
    }

    @Override
    public void onAction(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, getStrength()));
    }
}
