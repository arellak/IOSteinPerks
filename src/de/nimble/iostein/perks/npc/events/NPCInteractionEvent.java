package de.nimble.iostein.perks.npc.events;

import de.nimble.iostein.perks.npc.NPC;
import de.nimble.iostein.perks.npc.NPCClickAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NPCInteractionEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private NPC clicked;

    private Player player;

    private NPCClickAction clickAction;

    public NPCInteractionEvent(NPC clicked, Player player, NPCClickAction clickAction) {
        this.clicked = clicked;
        this.player = player;
        this.clickAction = clickAction;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public NPC getClicked() {
        return this.clicked;
    }

    public Player getPlayer() {
        return this.player;
    }

    public NPCClickAction getClickAction() {
        return this.clickAction;
    }

}
