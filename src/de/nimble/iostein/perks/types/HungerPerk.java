package de.nimble.iostein.perks.types;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;

public class HungerPerk extends Perk {

    public HungerPerk() {
        setId(PerksPlugin.perkConfig.getId("noHunger"));
        setName(PerksPlugin.perkConfig.getDisplayName("noHunger"));
        setDescription(PerksPlugin.perkConfig.getDescription("noHunger"));
        setStrength(PerksPlugin.perkConfig.getStrength("noHunger"));
        setType(PerkType.NO_HUNGER);
    }

    @Override
    public void onAction(Player player) {

    }

}
