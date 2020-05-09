package tomelo.cursed.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import tomelo.cursed.main.Driver;
import tomelo.cursed.platforms.StoneLand;

public class FarmListener implements Listener{
	
	Driver d;
	
	public FarmListener(Driver driver) {
		d = driver;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Location loc;
		Material player_item = e.getItem().getType();
		
		if(player_item == Material.WATER_BUCKET) {
			loc = e.getClickedBlock().getLocation();
			loc.setY(loc.getY()+1.0);
			e.setCancelled(true);
			placeStone(loc);
			loc.getWorld().strikeLightningEffect(loc); // lighting will strike, but will not do damage
			e.getPlayer().getInventory().getItemInMainHand().setType(Material.BUCKET);
			return;
		}
	}
	
	
	// creates platform of stone (Stone Land) that will spawn seeds
	private void placeStone(Location loc) {
		double starter_x = loc.getX()-2;
		double starter_z = loc.getZ()-2;
		double end_x = loc.getX()+3;
		double end_z = loc.getZ()+3;
		
		new StoneLand(loc, (int) starter_x, (int) starter_z, (int) end_x, (int) end_z, d);		
	}
	
}
