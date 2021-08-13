package de.nimble.iostein.perks.npc;

import com.comphenix.protocol.wrappers.EnumWrappers;

/**
 * Different types of click actions
 */
public enum NPCClickAction {

    INTERACT,
    INTERACT_AT,
    ATTACK;

    public static NPCClickAction fromAction(EnumWrappers.EntityUseAction action) {
        switch(action) {
            case ATTACK:
                return ATTACK;
            case INTERACT:
                return INTERACT;
            case INTERACT_AT:
                return INTERACT_AT;
            default:
                return null;
        }
    }


}
