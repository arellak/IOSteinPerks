package de.nimble.iostein.perks.types;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;

public class GravityPerk extends Perk {

    public GravityPerk() {
        init("noGravity", PerkType.NO_GRAVITY);
    }

    @Override
    public void onAction(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
    }

}
