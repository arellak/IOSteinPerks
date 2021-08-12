package de.nimble.iostein.commands;

import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.npc.NPC;
import de.nimble.iostein.perks.npc.NPCOptions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) {
            return false;
        }
        Player player = (Player) commandSender;

        NPCOptions npcOptions = new NPCOptions();
        npcOptions.setUuid(PerksPlugin.generalConfig.getString("npc.uuid"));
        npcOptions.setName(PerksPlugin.generalConfig.getString("npc.name"));
        npcOptions.setLocation(player.getLocation());

        NPC npc = PerksPlugin.npcManager.newNPC(npcOptions);

        for(Player p : Bukkit.getOnlinePlayers()) {
            npc.showTo(p);
        }

        player.sendMessage(PerksPlugin.generalConfig.getPerkVillagerSpawnedMessage());

        return true;
    }

}
