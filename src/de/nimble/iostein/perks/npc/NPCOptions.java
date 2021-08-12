package de.nimble.iostein.perks.npc;

import org.bukkit.Location;

public class NPCOptions {

    private String name;
    private String texture;
    private String signature;
    private String uuid;
    private Location location;
    private boolean hideNametag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public boolean isHideNametag() {
        return hideNametag;
    }

    public void setHideNametag(boolean hideNametag) {
        this.hideNametag = hideNametag;
    }
}
