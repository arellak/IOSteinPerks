package de.nimble.iostein.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NPCLocationConfig extends BaseConfig {

    public NPCLocationConfig() {
        super("npcLocations");
    }

    public void saveLocation(Location loc) {
        String locString = locToString(loc);
        List<String> locStrings = getLocationStrings();
        locStrings.add(locString);
        configuration.set("locations", locStrings);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteLocation(Location loc) {
        String locString = locToString(loc);
        List<String> locStrings = getLocationStrings();
        locStrings.remove(locString);

        configuration.set("locations", locStrings);
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        for(String locString : configuration.getStringList("locations")) {
            locations.add(stringToLoc(locString));
        }

        return locations;
    }

    public List<String> getLocationStrings() {
        return configuration.getStringList("locations") == null ? new ArrayList<>() : configuration.getStringList("locations");
    }

    public String locToString(Location loc) {
        return loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getWorld().getName();
    }

    public Location stringToLoc(String locString) {
        String[] locParts = locString.split(";");
        double blockX = Double.parseDouble(locParts[0]);
        double blockY = Double.parseDouble(locParts[1]);
        double blockZ = Double.parseDouble(locParts[2]);
        World world = Bukkit.getWorld(locParts[3]);

        return new Location(world, blockX, blockY, blockZ);
    }

}
