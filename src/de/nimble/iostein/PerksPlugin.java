package de.nimble.iostein;

import de.nimble.iostein.commands.NPCCommand;
import de.nimble.iostein.commands.PerkCommand;
import de.nimble.iostein.config.*;
import de.nimble.iostein.listener.*;
import de.nimble.iostein.perks.npc.NPC;
import de.nimble.iostein.perks.npc.NPCManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PerksPlugin extends JavaPlugin {

    public static PerkConfig perkConfig;
    public static GeneralPerkConfig generalConfig;
    public static InventoryConfig inventoryConfig;
    public static NPCLocationConfig npcLocationConfig;
    public static PerkStates perkStates;

    public static NPCManager npcManager;

    @Override
    public void onEnable() {
        perkConfig = new PerkConfig();
        generalConfig = new GeneralPerkConfig();
        inventoryConfig = new InventoryConfig();
        npcLocationConfig = new NPCLocationConfig();
        perkStates = new PerkStates();

        npcManager = new NPCManager(this);

        loadConfig();
        loadCommands();
        registerEvents(getServer().getPluginManager());

        npcManager.spawnAllNPCs();
    }

    @Override
    public void onDisable() {
        for(NPC npc : npcManager.getRegisteredNPCs()) {
            npc.hideFromEveryone();
        }
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void loadCommands() {
        getCommand("perks").setExecutor(new PerkCommand());
        getCommand("spawnNPC").setExecutor(new NPCCommand());
    }

    public void registerEvents(PluginManager pluginManager) {
        pluginManager.registerEvents(new DamageListener(), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
        pluginManager.registerEvents(new NPCClickListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PerkInventoryListener(), this);
    }

}
