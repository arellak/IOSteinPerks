package de.nimble.iostein.listener;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkPlayer;
import de.nimble.iostein.perks.PerkPlayerManager;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        PerkPlayer perkPlayer = PerkPlayerManager.getInstance().getPerkPlayerByUUID(player.getUniqueId());
        if(perkPlayer == null) {
            return;
        }

        for(Perk perk : perkPlayer.getPerks()) {
            if(perk.getType() == PerkType.NO_HUNGER) {
                event.setFoodLevel(40);
            }
        }
    }

}
