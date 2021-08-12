package de.nimble.iostein.perks.npc;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class NMSHelper {

    private static NMSHelper instance;

    public static NMSHelper getInstance() {
        if(instance == null) {
            instance = new NMSHelper();
        }
        return instance;
    }

    private NMSHelper() {

    }

    public void sendPacket(Player player, Object packet) {
        if(player == null) {
            return;
        }

        Object handle = getHandle(player);
        Object playerConnection = null;
        try {
            playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Object getHandle(Player player) {
        try {
            return player.getClass().getMethod("getHandle").invoke(player);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server.v1_16_R3." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}