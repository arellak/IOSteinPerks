package de.nimble.iostein.perks;

/**
 * Enum for all the different Perk types so you are able to customize your perks a bit better<br>
 * e.g. "Speed"-Perk with the PerkType NO_FALL_DAMAGE, SPEED and a strength of 2
 */
public enum PerkType {

    NO_FALL_DAMAGE,
    NO_FIRE_DAMAGE,
    NO_GRAVITY,
    UNDERWATER_BREATHING,
    SPEED,
    STRENGTH,
    NO_HUNGER,
    PREFIX,
    NONE
    ;

    /**
     *
     * @param name A perk type read from a config for example
     * @return either returns <b>NONE</b> or the Perk that equals the name in the param
     */
    public static PerkType getTypeByName(String name) {
        for(PerkType type : PerkType.values()) {
            if(type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return PerkType.NONE;
    }

}
