package de.nimble.iostein.perks.types;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.npc.PerkItem;
import org.bukkit.entity.Player;

public class GravityPerk extends Perk {

    public GravityPerk() {
        setId(PerksPlugin.perkConfig.getId("noGravity"));
        setName(PerksPlugin.perkConfig.getDisplayName("noGravity"));
        setDescription(PerksPlugin.perkConfig.getDescription("noGravity"));
        setStrength(PerksPlugin.perkConfig.getStrength("noGravity"));
        setType(PerkType.NO_GRAVITY);
        setPerkItem(new PerkItem(getName(), PerksPlugin.perkConfig.getItem("noGravity")));
    }

    @Override
    public void onAction(Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
    }

}
