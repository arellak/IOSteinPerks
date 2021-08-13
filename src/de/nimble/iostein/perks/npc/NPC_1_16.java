package de.nimble.iostein.perks.npc;

import com.mojang.authlib.GameProfile;
import de.nimble.iostein.PerksPlugin;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class NPC_1_16 implements NPC{

    private JavaPlugin plugin;

    private UUID uuid;
    private String name;
    private String entityName;
    private Set<UUID> viewers = new HashSet<>();

    private EntityPlayer entityPlayer;

    public NPC_1_16(JavaPlugin plugin, NPCOptions options) {
        this.plugin = plugin;

        this.name = options.getName();
        this.uuid = UUID.fromString(options.getUuid());

        this.entityName = this.name;

        addToWorld(options.getLocation());
    }

    /**
     * Basically method for initializing the NPC / EntityPlayer
     * @param location where npc should be spawned
     */
    private void addToWorld(Location location) {
        if(location.getWorld() == null) {
            throw new IllegalArgumentException("NPC location world cannot be null");
        }

        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.fromString(PerksPlugin.generalConfig.getString("npc.uuid")), name);

        entityPlayer = new EntityPlayer(
                nmsServer,
                nmsWorld,
                gameProfile,
                new PlayerInteractManager(nmsWorld)
        );

        entityPlayer.setLocation(
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );

        entityPlayer.setCustomName(new ChatComponentText(name));
        entityPlayer.setCustomNameVisible(true);

        entityPlayer.setInvulnerable(true);
        entityPlayer.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0);

        entityPlayer.maxNoDamageTicks = Integer.MAX_VALUE;
        entityPlayer.canPickUpLoot = false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUuid() {
        return entityPlayer.getUniqueID();
    }

    @Override
    public int getId() {
        return entityPlayer.getId();
    }

    /**
     * Sends packets that there is a new NPC and that the given Player can see the NPC now
     * @param player to which the npc should be revealed
     */
    @Override
    public void showTo(Player player) {
        viewers.add(player.getUniqueId());

        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER,
                entityPlayer
        );
        sendPacket(player, packetPlayOutPlayerInfo);

        PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(
                entityPlayer
        );
        sendPacket(player, packetPlayOutNamedEntitySpawn);

        Bukkit.getServer().getScheduler().runTaskTimer(plugin, task -> {
            Player currentlyOnline = Bukkit.getPlayer(player.getUniqueId());
            if (currentlyOnline == null ||
                    !currentlyOnline.isOnline() ||
                    !viewers.contains(player.getUniqueId())) {
                task.cancel();
                return;
            }

            sendHeadRotationPacket(player);
        }, 0, 2);
    }

    /**
     * sends packet that player can't see npc anymore
     * @param player
     */
    @Override
    public void hideFrom(Player player) {
        if (!viewers.contains(player.getUniqueId())) return;
        viewers.remove(player.getUniqueId());

        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entityPlayer.getId());
        sendPacket(player, packet);
    }

    @Override
    public void delete() {
        Set<Player> onlineViewers = viewers.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        onlineViewers.forEach(this::hideFrom);

        PerksPlugin.npcLocationConfig.deleteLocation(getLocation());
    }

    /**
     * follows the player with the head
     * @param player
     */
    public void sendHeadRotationPacket(Player player) {
        Location original = getLocation();
        Location location = original.clone().setDirection(player.getLocation().subtract(original.clone()).toVector());

        byte yaw = (byte) (location.getYaw() * 256 / 360);
        byte pitch = (byte) (location.getPitch() * 256 / 360);

        PacketPlayOutEntityHeadRotation headRotationPacket = new PacketPlayOutEntityHeadRotation(
                this.entityPlayer,
                yaw
        );
        sendPacket(player, headRotationPacket);

        PacketPlayOutEntity.PacketPlayOutEntityLook lookPacket = new PacketPlayOutEntity.PacketPlayOutEntityLook(
                getId(),
                yaw,
                pitch,
                false
        );
        sendPacket(player, lookPacket);
    }
    
    @Override
    public Location getLocation() {
        return new Location(
                entityPlayer.getWorld().getWorld(),
                entityPlayer.locX(),
                entityPlayer.locY(),
                entityPlayer.locZ(),
                entityPlayer.yaw,
                entityPlayer.pitch
        );
    }

    @Override
    public void hideFromEveryone() {
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!viewers.contains(onlinePlayer.getUniqueId())) return;
            viewers.remove(onlinePlayer.getUniqueId());

            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(entityPlayer.getId());
            sendPacket(onlinePlayer, packet);
        }
    }

    private void sendPacket(Player player, Object packet) {
        NMSHelper.getInstance().sendPacket(player, packet);
    }

}
