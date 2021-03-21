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
        
        Block leftBlock;
        Block rightBlock;
        switch ((int) cartLocation.getYaw()) {
        case 0:
        	leftBlock = rail.getRelative(BlockFace.NORTH);
        	rightBlock = rail.getRelative(BlockFace.SOUTH);
        	break;
        case 90:
        	leftBlock = rail.getRelative(BlockFace.EAST);
        	rightBlock = rail.getRelative(BlockFace.WEST);
        	break;
        case 180:
        	leftBlock = rail.getRelative(BlockFace.SOUTH);
        	rightBlock = rail.getRelative(BlockFace.NORTH);
        	break;
        case 270:
        	leftBlock = rail.getRelative(BlockFace.WEST);
        	rightBlock = rail.getRelative(BlockFace.SOUTH);
        	break;
        default:
        	return; // Not sure how this would ever happen, but the code complains.
        }
        
        if (leftBlock.getType() != Material.BELL || 
        	rightBlock.getType() != Material.BELL || 
        	leftBlock.getRelative(BlockFace.DOWN).getType() != Material.REDSTONE_BLOCK ||
        	rightBlock.getRelative(BlockFace.DOWN).getType() != Material.REDSTONE_BLOCK) {
        	cart.setMaxSpeed(BUKKIT_SPEED_MULTIPLIER);
        	return;
        }
        
        cart.setMaxSpeed(BUKKIT_SPEED_MULTIPLIER * 4d);
    }
}
