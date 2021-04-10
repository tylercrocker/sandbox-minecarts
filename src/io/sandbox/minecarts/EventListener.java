package io.sandbox.minecarts;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import io.sandbox.helpers.OutputHelper;

public class EventListener implements Listener {
	OutputHelper output;
	private static final double BUKKIT_SPEED_MULTIPLIER = 0.4d;
    
    public EventListener(OutputHelper theOutputHelper) {
    	output = theOutputHelper;
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
        if (rail.getRelative(BlockFace.DOWN).getType() != Material.REDSTONE_BLOCK) {
        	cart.setMaxSpeed(BUKKIT_SPEED_MULTIPLIER);
        	return;
        }
        
        Block sideOne;
        Block sideTwo;
        switch (cart.getFacing().name()) {
        case "NORTH":
        case "SOUTH":
        	sideOne = rail.getRelative(BlockFace.WEST);
        	sideTwo = rail.getRelative(BlockFace.EAST);
        	break;
        case "EAST":
        case "WEST":
        	sideOne = rail.getRelative(BlockFace.NORTH);
        	sideTwo = rail.getRelative(BlockFace.SOUTH);
        	break;
        default:
        	// This only happens when the mine cart is just entering the block and can be ignored.
        	return;
        }
        
        if (sideOne.getType() != Material.END_ROD || 
        	sideTwo.getType() != Material.END_ROD || 
        	sideOne.getRelative(BlockFace.DOWN).getType() != Material.GOLD_BLOCK ||
        	sideTwo.getRelative(BlockFace.DOWN).getType() != Material.GOLD_BLOCK) {
        	cart.setMaxSpeed(BUKKIT_SPEED_MULTIPLIER);
        	return;
        }
        
        cart.setMaxSpeed(BUKKIT_SPEED_MULTIPLIER * 4d);
    }
}
