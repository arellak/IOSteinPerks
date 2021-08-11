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

        Perk perk = PerkManager.getInstance().getPerkByType(type);

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
