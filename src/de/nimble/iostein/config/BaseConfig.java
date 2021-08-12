package de.nimble.iostein.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class BaseConfig {

    protected String fileName;
    protected File file;
    protected FileConfiguration configuration;

    public BaseConfig(String fileName) {
        this.fileName = fileName;
        this.file = new File("plugins/iosteinPerks/" + fileName + ".yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', configuration.getString(path));
    }

    public int getInt(String name) {
        return this.configuration.getInt(name);
    }

}
