package io.sandbox.minecarts;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class EventListener implements Listener {
	private static final double BUKKIT_SPEED_MULTIPLIER = 0.4d;

    private Material boostBlock;

    public void MinecartListener(Material boostBlock) {
        this.boostBlock = boostBlock;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVehicleMove(VehicleMoveEvent event) {
    	Vehicle eventVehicle = event.getVehicle();
    	if (!(eventVehicle instanceof Minecart)) { return; }

        Minecart cart = (Minecart) eventVehicle;
        World cartsWorld = cart.getWorld();
        Location cartLocation = cart.getLocation();
        Block rail = cartsWorld.getBlockAt(cartLocation);
        if (rail.getType() != Material.POWERED_RAIL) { return; }
        
        Block blockBelow = cartsWorld.getBlockAt(cartLocation.add(0, -1, 0));
        if (blockBelow.getType() == boostBlock) {
            cart.setMaxSpeed(BUKKIT_SPEED_MULTIPLIER * 4);
        } else {
            cart.setMaxSpeed(BUKKIT_SPEED_MULTIPLIER);
        }
    }
}
