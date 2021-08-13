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

    /*
    All the different configs that are need
    I saved them in one space so I can init them on the server start and know excatly where they are
    they are static because I need them all the time and those are no big objects so they can chill in the memory
     */
    public static PerkConfig perkConfig;
    public static GeneralPerkConfig generalConfig;
    public static InventoryConfig inventoryConfig;
    public static NPCLocationConfig npcLocationConfig;
    public static PerkStates perkStates;

    // Manager for the NPCs
    public static NPCManager npcManager;

    @Override
    public void onEnable() {
        // just some initialization for the configs and the manager
        perkConfig = new PerkConfig();
        generalConfig = new GeneralPerkConfig();
        inventoryConfig = new InventoryConfig();
        npcLocationConfig = new NPCLocationConfig();
        perkStates = new PerkStates();

        npcManager = new NPCManager(this);

        loadConfig();
        loadCommands();
        registerEvents(getServer().getPluginManager());

        // spawn all NPCs on startup
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
        pluginManager.registerEvents(new InventoryCloseListener(), this);
    }

}
