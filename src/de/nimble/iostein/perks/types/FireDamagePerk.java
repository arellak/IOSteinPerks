package de.nimble.iostein.perks.types;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;

public class FireDamagePerk extends Perk {

    public FireDamagePerk() {
        setId(PerksPlugin.perkConfig.getId("noFireDamage"));
        setName(PerksPlugin.perkConfig.getDisplayName("noFireDamage"));
        setDescription(PerksPlugin.perkConfig.getDescription("noFireDamage"));
        setStrength(PerksPlugin.perkConfig.getStrength("noFireDamage"));
        setType(PerkType.NO_FIRE_DAMAGE);
    }

    @Override
    public void onAction(Player player) {

    }

}
