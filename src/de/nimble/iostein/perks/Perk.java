package de.nimble.iostein.perks;

import org.bukkit.entity.Player;

/**
 * The Standard Perk class from which each PerkClass will inherit
 *
 */
public abstract class Perk {

    private int id;
    private String name;
    private String description;
    private int strength;
    private PerkType type;

    /**
     * This method will be overwritten in each child class and in it will befined what the different Perks do
     */
    public abstract void onAction(Player player);
    
    /*
     * Just the getter and setters for the attributes
     */
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setType(PerkType type) {
        this.type = type;
    }

    public PerkType getType() {
        return this.type;
    }

}
