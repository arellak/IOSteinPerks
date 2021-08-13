package de.nimble.iostein.perks.types;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;

public class PrefixPerk extends Perk {

    public PrefixPerk() {
        init("prefix", PerkType.PREFIX);
    }

    @Override
    public void onAction(Player player) {
        player.sendMessage("This is the prefix perk...");
    }
}
