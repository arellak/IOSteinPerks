package de.nimble.iostein.perks.npc;

import org.bukkit.Location;

/**
Options for the NPC...basically just its essentials
 */
public class NPCOptions {

    private String name;
    private String uuid;
    private Location location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
