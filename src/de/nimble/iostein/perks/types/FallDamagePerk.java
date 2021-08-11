package de.nimble.iostein.perks.types;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.npc.PerkItem;
import org.bukkit.entity.Player;

public class FallDamagePerk extends Perk {

    public FallDamagePerk() {
        setId(PerksPlugin.perkConfig.getId("noFallDamage"));
        setName(PerksPlugin.perkConfig.getDisplayName("noFallDamage"));
        setDescription(PerksPlugin.perkConfig.getDescription("noFallDamage"));
        setStrength(PerksPlugin.perkConfig.getStrength("noFallDamage"));
        setType(PerkType.NO_FALL_DAMAGE);
        setPerkItem(new PerkItem(getName(), PerksPlugin.perkConfig.getItem("noFallDamage")));
    }

    @Override
    public void onAction(Player player) {
        // nothing else to do here...
        System.out.println("Removed falldamage from player...");
    }

}
