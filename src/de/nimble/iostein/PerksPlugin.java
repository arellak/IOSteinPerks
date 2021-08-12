package de.nimble.iostein;

import de.nimble.iostein.commands.NPCCommand;
import de.nimble.iostein.commands.PerkCommand;
import de.nimble.iostein.config.GeneralPerkConfig;
import de.nimble.iostein.config.InventoryConfig;
import de.nimble.iostein.config.PerkConfig;
import de.nimble.iostein.listener.*;
import de.nimble.iostein.perks.npc.NPCManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PerksPlugin extends JavaPlugin {

    public static PerkConfig perkConfig;
    public static GeneralPerkConfig generalConfig;
    public static InventoryConfig inventoryConfig;

    public static NPCManager npcManager;

    @Override
    public void onEnable() {
        perkConfig = new PerkConfig("perks");
        generalConfig = new GeneralPerkConfig();
        inventoryConfig = new InventoryConfig();

        npcManager = new NPCManager(this);

        loadConfig();
        loadCommands();
        registerEvents(getServer().getPluginManager());
    }

    @Override
    public void onDisable() {

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
