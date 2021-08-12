package de.nimble.iostein.perks.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface NPC {

    String getName();
    UUID getUuid();
    int getId();
    void showTo(Player player);
    void hideFrom(Player player);
    void delete();
    Location getLocation();

}
