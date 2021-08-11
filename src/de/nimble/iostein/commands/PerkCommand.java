package de.nimble.iostein.commands;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkPlayerManager;
import de.nimble.iostein.perks.PerkType;
import de.nimble.iostein.perks.types.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PerkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player player = (Player) commandSender;
        if(args.length < 2) {
            return false;
        }

        PerkType type = PerkType.getTypeByName(args[1]);
        Perk perk = null;

        switch (type) {
            case STRENGTH:
                perk = new StrengthPerk();
                break;
            case SPEED:
                perk = new SpeedPerk();
                break;
            case NO_FALL_DAMAGE:
                perk = new FallDamagePerk();
                break;
            case NO_FIRE_DAMAGE:
                perk = new FireDamagePerk();
                break;
            case NO_GRAVITY:
                perk = new GravityPerk();
                break;
            case UNDERWATER_BREATHING:
                perk = new UnderwaterBreathingPerk();
                break;
            case NO_HUNGER:
                perk = new HungerPerk();
                break;
            default:
                break;
        }

        if(args[0].equalsIgnoreCase("add")) {
            String message = PerkPlayerManager.getInstance().addPerk(player.getDisplayName(), perk);
            player.sendMessage(message);

            PerkPlayerManager.getInstance().action();
        } else if(args[0].equalsIgnoreCase("remove")){
            String message = PerkPlayerManager.getInstance().removePerk(player.getDisplayName(), perk);
            player.sendMessage(message);
        }
        return true;
    }

}
