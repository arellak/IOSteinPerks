package de.nimble.iostein.perks.npc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.nimble.iostein.perks.npc.events.NPCInteractionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NPCManager {

    private JavaPlugin plugin;

    private Set<NPC> registeredNPCs = new HashSet<>();

    private Cache<Player, NPC> clickedNPCCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1L, TimeUnit.SECONDS)
            .build();

    public NPCManager(JavaPlugin plugin) {
        this.plugin = plugin;

        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, PacketType.Play.Client.USE_ENTITY) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        EnumWrappers.EntityUseAction useAction = event.getPacket().getEntityUseActions().read(0);
                        int entityId = event.getPacket().getIntegers().read(0);
                        handleEntityClick(event.getPlayer(), entityId, NPCClickAction.fromAction(useAction));
                    }
                }
        );
    }

    private void handleEntityClick(Player player, int entityId, NPCClickAction action) {
        registeredNPCs.stream()
                .filter(npc -> npc.getId() == entityId)
                .forEach(npc -> Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                    NPC previouslyClickedNPC = clickedNPCCache.getIfPresent(player);
                    if (previouslyClickedNPC != null && previouslyClickedNPC.equals(npc)) return; // If they've clicked this same NPC in the last 0.5 seconds ignore this click
                    clickedNPCCache.put(player, npc);

                    NPCInteractionEvent event = new NPCInteractionEvent(npc, player, action);
                    Bukkit.getPluginManager().callEvent(event);
                }, 2));
    }

    public NPC newNPC(NPCOptions options) {
        NPC npc = new NPC_1_16(plugin, options);
        registeredNPCs.add(npc);
        return npc;
    }

    public Optional<NPC> findNPC(String name) {
        return registeredNPCs.stream()
                .filter(npc -> npc.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public void deleteNPC(NPC npc) {
        npc.delete();
        registeredNPCs.remove(npc);
    }

    public void deleteAllNPCs() {
        Set<NPC> npcsCopy = new HashSet<>(registeredNPCs);
        npcsCopy.forEach(this::deleteNPC);
    }

    public Set<NPC> getRegisteredNPCs() {
        return registeredNPCs;
    }
}
