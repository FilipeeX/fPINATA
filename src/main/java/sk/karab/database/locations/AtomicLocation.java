package sk.karab.database.locations;

import org.bukkit.Location;

public class AtomicLocation {


    private Location location;


    public AtomicLocation(Location initialValue) {
        this.location = initialValue;
    }


    public void set(Location location) {
        this.location = location;
    }


    public Location get() {
        return this.location;
    }


}
