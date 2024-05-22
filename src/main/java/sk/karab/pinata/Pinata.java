package sk.karab.pinata;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class Pinata {


    public static ArrayList<Pinata> pinatas = new ArrayList<>();
    private final LivingEntity camel;


    public Pinata(Location location) {

        World world = location.getWorld();
        assert world != null;

        Entity uncastedCamel = world.spawnEntity(location, EntityType.CAMEL, false);
        if (!(uncastedCamel instanceof LivingEntity)) {
            camel = null; // only for IDE to stop flagging this, useless
            return;
        }
        camel = (LivingEntity) uncastedCamel;

        pinatas.add(this);
    }


    public Entity getCamel() {
        return camel;
    }


    public void died() {
        pinatas.remove(this);
    }


    public static ArrayList<Pinata> getPinatas() {
        return pinatas;
    }


    public static Pinata getByEntity(LivingEntity entity) {

        for (Pinata pinata : pinatas) {
            if (pinata.getCamel() == entity) {
                return pinata;
            }
        }

        return null;
    }


}
