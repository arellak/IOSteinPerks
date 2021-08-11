package de.nimble.iostein.perks.npc;

import com.mojang.authlib.GameProfile;
import de.nimble.iostein.PerksPlugin;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class PerkNPC {

    private Location location;
    private String name;
    private String uuid;

    private EntityPlayer npc;
    private MinecraftServer nmsServer;
    private WorldServer nmsWorld;
    private GameProfile gameProfile;

    public PerkNPC(Player player) {
        this.location = player.getLocation();
        this.name = ChatColor.translateAlternateColorCodes('&', PerksPlugin.generalConfig.getNPCName());

        nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        gameProfile = new GameProfile(UUID.fromString("94ee3e42-8877-4f01-8617-4aad6aa7c277"), name);

        npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

        npc.setCustomName(new ChatComponentText(name));
        npc.setCustomNameVisible(true);

        npc.setInvulnerable(true);
        npc.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0);

        npc.maxNoDamageTicks = Integer.MAX_VALUE;
        npc.canPickUpLoot = false;
    }

    public void spawn() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        }
    }

    public void onClick() {

    }

}
