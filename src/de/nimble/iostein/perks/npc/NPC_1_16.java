package de.nimble.iostein.perks.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
    private String texture;
    private String signature;
    private Set<UUID> viewers = new HashSet<>();

    private EntityPlayer entityPlayer;

    public NPC_1_16(JavaPlugin plugin, NPCOptions options) {
        this.plugin = plugin;

        this.name = options.getName();
        this.texture = options.getTexture();
        this.signature = options.getSignature();
        this.uuid = UUID.fromString(options.getUuid());

        this.entityName = this.name;

        addToWorld(options.getLocation());
    }

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

    private GameProfile makeGameProfile() {
        GameProfile gameProfile = new GameProfile(uuid, entityName);
        gameProfile.getProperties().put(
                "textures",
                new Property("textures",
                        texture,
                        signature
                )
        );
        return gameProfile;
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
    }

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

    public void fixSkinHelmetLayerForPlayer(Player player) {
        Byte skinFixByte = 0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40;
        sendMetadata(player, 16, skinFixByte);
    }

    private void sendMetadata(Player player, int index, byte o) {
        DataWatcher dataWatcher = entityPlayer.getDataWatcher();
        DataWatcherSerializer<Byte> registry = DataWatcherRegistry.a;
        dataWatcher.set(
                registry.a(index),
                o
        );

        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(
                getId(),
                dataWatcher,
                false
        );
        sendPacket(player, metadataPacket);
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

    private void sendPacket(Player player, Object packet) {
        NMSHelper.getInstance().sendPacket(player, packet);
    }

}
