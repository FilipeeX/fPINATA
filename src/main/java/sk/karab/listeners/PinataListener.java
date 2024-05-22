package sk.karab.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import sk.karab.pinata.Pinata;

public class PinataListener implements Listener {


    @EventHandler
    public void entityDies(EntityDeathEvent event) {

        LivingEntity entity = event.getEntity();
        Pinata pinata = Pinata.getByEntity(entity);

        if (pinata == null) return;

        pinata.died();
    }


}
