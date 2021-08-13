package de.nimble.iostein.listener;

import de.nimble.iostein.perks.Perk;
import de.nimble.iostein.perks.PerkPlayer;
import de.nimble.iostein.perks.PerkPlayerManager;
import de.nimble.iostein.perks.PerkType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            // get the perkplayer by the name of player that has caused this event
            PerkPlayer perkPlayer = PerkPlayerManager.getInstance().getPerkPlayerByUUID(player.getUniqueId().toString());

            // if the given perkPlayer is null I don't want to continue
            if(perkPlayer == null) {
                return;
            }

            if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                for(Perk perk : perkPlayer.getPerks()) {
                    if(perk.getType() == PerkType.NO_FALL_DAMAGE) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

}
