package de.nimble.iostein;

import de.nimble.iostein.commands.PerkCommand;
import de.nimble.iostein.config.PerkConfig;
import de.nimble.iostein.listener.DamageListener;
import de.nimble.iostein.listener.FoodLevelChangeListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PerksPlugin extends JavaPlugin {

    public static PerkConfig perkConfig;

    @Override
    public void onEnable() {
        perkConfig = new PerkConfig("perks");
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
    }

    public void registerEvents(PluginManager pluginManager) {
        pluginManager.registerEvents(new DamageListener(), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
    }

}
