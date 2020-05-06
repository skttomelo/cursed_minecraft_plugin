package tomelo.cursed;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BasicListener implements Listener{
	Driver d;
	Random r;
	
	Material[] random_material = {Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT, Material.PODZOL};
	Sound[] random_sound = {Sound.BLOCK_GRASS_PLACE, Sound.BLOCK_GRAVEL_PLACE};
		
	
	public BasicListener(Driver driver) {
		d = driver;
		r = new Random();
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Material broken_block = e.getBlock().getType(); 
		Material item_main_hand = e.getPlayer().getInventory().getItemInMainHand().getType();
		Location loc;
		
		// spawns elder guardian on player if they break dirt with a wooden sword
		if(item_main_hand == Material.WOODEN_SWORD && isRandDirt(broken_block)) {
			loc = new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getBlockX(), e.getPlayer().getLocation().getBlockY()+3, e.getPlayer().getLocation().getBlockZ());
			e.getPlayer().getWorld().spawnEntity(loc, EntityType.ELDER_GUARDIAN);
		}
		
		
		if(broken_block == Material.GRASS) {
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
			e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.OAK_LOG, 1));
			return;
		}
		if(broken_block == Material.OAK_LOG) {
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
			e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
			return;
		}
		if(broken_block == Material.STONE) {
			// we create a task in bukkit scheduler to change the material after 1 tick
			if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.STONE_PICKAXE) setBlockLava(e.getBlock());
			return;
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Material placed_block = e.getBlockPlaced().getType();
		if(placed_block == Material.OAK_PLANKS) {
			e.setCancelled(true);
			e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
			
			placeRandDirt(e.getBlockPlaced());
			return;
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Location loc;
		Material player_item = e.getItem().getType();
		Block clicked_block = e.getClickedBlock();
		// we blow the door up if the player clicks on the door
		// this will only get triggered if player has item in hand
		if(clicked_block.getType().toString().contains("DOOR")) {
			new BukkitRunnable() {
				public void run() {
					clicked_block.getWorld().createExplosion(clicked_block.getLocation(), 4f);
				}
			}.runTask(d);
		}
		
		
		if(player_item == Material.POPPY) {
			loc = e.getClickedBlock().getLocation();
			loc.setY(loc.getY()+1.0);
			e.getPlayer().getWorld().dropItem(loc, new ItemStack(Material.STICK, 1));
			e.setCancelled(true);
			e.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
			return;
		}
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
	
	// creates an odd shaped platform around the center loc
	private void placeStone(Location loc) {
		new BukkitRunnable() {
			public void run() {
				double starter_x = loc.getX()-2;
				double starter_z = loc.getZ()-2;
				double end_x = loc.getX()+3;
				double end_z = loc.getZ()+3;
				
				for(double x = starter_x; x<end_x; x++) {
					for(double z = starter_z; z<end_z; z++) {
						loc.getWorld().getBlockAt((int) x, (int) loc.getY(), (int) z).setType(Material.STONE);
					}
				}
			}
		}.runTask(d);
	}
	
	private void placeRandDirt(Block b) {
		new BukkitRunnable() {
			public void run() {
				int rand_mat = r.nextInt(random_material.length);
				int rand_sound = 0;
				if(rand_mat != 0) {
					rand_sound = 1;
				}
				
				// random(highest_val - lowest_val) + lowest_val
				int rand_x = r.nextInt((b.getLocation().getBlockX()+2)-(b.getLocation().getBlockX()-1))+(b.getLocation().getBlockX()-1);
				int rand_z = r.nextInt((b.getLocation().getBlockZ()+2)-(b.getLocation().getBlockZ()-1))+(b.getLocation().getBlockZ()-1);
				
				Location loc = new Location(b.getWorld(), rand_x, b.getLocation().getBlockY(), rand_z);
				
				// if location isn't air we want to place the block above that one
				while(loc.getBlock().getType() != Material.AIR) {
					loc = new Location(loc.getWorld(), loc.getX(), loc.getY()+1, loc.getZ());
				}
				loc.getWorld().playSound(b.getLocation(), random_sound[rand_sound], 1, .75f);
				loc.getBlock().setType(random_material[rand_mat]);
			}
		}.runTask(d);
	}
	
	private void setBlockLava(Block b) {
		new BukkitRunnable() {
			public void run() {
				b.setType(Material.LAVA);
			}
		}.runTask(d);
	}
	
	private boolean isRandDirt(Material mat) {
		for(Material m : random_material) {
			if(mat == m) {
				return true;
			}
		}
		return false;
	}
}
