package tomelo.cursed.platforms;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import tomelo.cursed.main.Driver;

public class StoneLand {
	int starter_x = 0;
	int starter_z = 0;
	int end_x = 0;
	int end_z = 0;
	
	Random r;

	Material[] seeds = {Material.BEETROOT_SEEDS, Material.MELON_SEEDS, Material.WHEAT_SEEDS};
	Sound egg_noise;
	
	BukkitRunnable spawn_seeds;
	
	int task_id = -1;
	
	
	public StoneLand(Location loc, int starter_x, int starter_z, int end_x, int end_z, Driver d) {
		this.starter_x = starter_x;
		this.starter_z = starter_z;
		this.end_x = end_x;
		this.end_z = end_z;
		
		egg_noise = Sound.ENTITY_CHICKEN_EGG;
		
		r = new Random();
		
		spawn_seeds = new BukkitRunnable() {
			boolean seeds_spawned = false;
			public void run() {
				for(int x = starter_x; x<end_x; x++) {
					for(int z = starter_z; z<end_z; z++) {
						// chance to spawn a random seed
						if(r.nextInt(100) > 50) {
							loc.getWorld().dropItemNaturally(getRandLocation(loc), new ItemStack(seeds[r.nextInt(seeds.length)], 1));
							seeds_spawned = true;
						}
					}
				}
				
				// stop creating seeds
				if(seeds_spawned == true) {
					spawn_seeds.cancel();
				}
			}
		};
		
		new BukkitRunnable() {
			public void run() {
				for(int x = starter_x; x<end_x; x++) {
					for(int z = starter_z; z<end_z; z++) {
						loc.getWorld().getBlockAt(x, loc.getBlockY(), z).setType(Material.STONE);
						loc.getWorld().getBlockAt(x, loc.getBlockY()-1, z).setType(Material.WATER);
					}
				}
				spawn_seeds.runTaskTimer(d, 250, 15); // wait 15 ticks to start trying to spawn seeds (this will happen every 30 ticks)
			}
		}.runTask(d);
	}
	
	// get a random location within the area of the stone land
	public Location getRandLocation(Location loc) {
		
		int x = r.nextInt(end_x-starter_x)+starter_x;
		int z = r.nextInt(end_z-starter_z)+starter_z;
		
		return new Location(loc.getWorld(), x, loc.getBlockY()+1, z);
	}
}
