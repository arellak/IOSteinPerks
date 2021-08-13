package de.nimble.iostein.perks.npc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.nimble.iostein.PerksPlugin;
import de.nimble.iostein.perks.npc.events.NPCInteractionEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
                        // The action...attack, interact, interact_at
                        EnumWrappers.EntityUseAction useAction = event.getPacket().getEntityUseActions().read(0);
                        int entityId = event.getPacket().getIntegers().read(0);

                        handleEntityClick(event.getPlayer(), entityId, NPCClickAction.fromAction(useAction));
                    }
                }
        );
    }

    private void handleEntityClick(Player player, int entityId, NPCClickAction action) {
        /*
        searches for npc with the given id
        for this npc it runs a scheduler that is 2 Ticks long so you don't spam the packet and something happens 5 times or so
        for this procedure the cache is used

        calls NPCInteractionEvent with the needed parameters
         */
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

    public void spawnAllNPCs() {
        for(Location loc : PerksPlugin.npcLocationConfig.getLocations()) {
            spawnNPC(loc);
        }
    }

    public NPC spawnNPC(Location loc) {
        NPCOptions npcOptions = new NPCOptions();
        npcOptions.setUuid(PerksPlugin.generalConfig.getString("npc.uuid"));
        npcOptions.setName(PerksPlugin.generalConfig.getString("npc.name"));
        npcOptions.setLocation(loc);

        NPC npc = newNPC(npcOptions);

        for(Player p : Bukkit.getOnlinePlayers()) {
            npc.showTo(p);
        }

        return npc;
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
