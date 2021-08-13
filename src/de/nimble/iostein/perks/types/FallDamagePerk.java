package de.nimble.iostein.perks.types;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;

public class FallDamagePerk extends Perk {

    public FallDamagePerk() {
        init("noFallDamage", PerkType.NO_FALL_DAMAGE);
    }

    @Override
    public void onAction(Player player) {
        // nothing else to do here...
    }

}
