package de.nimble.iostein.perks.types;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkType;

import java.util.ArrayList;
import java.util.List;

public class PerkManager {

    private List<Perk> perks;
    private static PerkManager instance;

    public static PerkManager getInstance() {
        if(instance == null) {
            instance = new PerkManager();
        }

        return instance;
    }

    private PerkManager() {
        this.perks = new ArrayList<>();
        initPerks();
    }

    private void initPerks() {
        perks.add(new FallDamagePerk());
        perks.add(new FireDamagePerk());
        perks.add(new GravityPerk());
        perks.add(new HungerPerk());
        perks.add(new PrefixPerk());
        perks.add(new SpeedPerk());
        perks.add(new StrengthPerk());
        perks.add(new UnderwaterBreathingPerk());
    }

    public Perk getPerkByType(PerkType type) {
        for(Perk perk : perks) {
            if(perk.getType() == type) {
                return perk;
            }
        }
        return null;
    }

    public List<Perk> getPerks() {
        return this.perks;
    }
}
