package de.nimble.iostein.perks.types;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;

public class HungerPerk extends Perk {

    public HungerPerk() {
        init("noHunger", PerkType.NO_HUNGER);

    }

    @Override
    public void onAction(Player player) {

    }

}
