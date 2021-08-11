package de.nimble.iostein.perks.types;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.npc.PerkItem;
import org.bukkit.entity.Player;

public class PrefixPerk extends Perk {

    public PrefixPerk() {
        setId(PerksPlugin.perkConfig.getId("prefix"));
        setName(PerksPlugin.perkConfig.getDisplayName("prefix"));
        setDescription(PerksPlugin.perkConfig.getDescription("prefix"));
        setStrength(PerksPlugin.perkConfig.getStrength("prefix"));
        setType(PerkType.PREFIX);
        setPerkItem(new PerkItem(getName(), PerksPlugin.perkConfig.getItem("prefix")));
    }

    @Override
    public void onAction(Player player) {
        player.sendMessage("This is the prefix perk...");
    }
}
