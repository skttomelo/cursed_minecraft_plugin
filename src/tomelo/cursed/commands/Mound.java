package tomelo.cursed.commands;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tomelo.cursed.main.Driver;

public class Mound implements CommandExecutor{
	Random r;
	Driver d;
	Material[] random_material = {Material.GRASS_BLOCK, Material.DIRT, Material.COARSE_DIRT, Material.PODZOL};
	
	public Mound(Driver driver) {
		r = new Random();
		d = driver;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Location loc = player.getLocation();
			int x = r.nextInt(5-3)+3+loc.getBlockX();
			int y = loc.getBlockY();
			int z = r.nextInt(5-3)+3+loc.getBlockZ();
			int mound_height = r.nextInt(17-3)+3+loc.getBlockY();
			player.sendMessage(""+x+","+y+","+z+","+mound_height);
			
			new BukkitRunnable() {
				public void run() {
					for(int i = y; i<mound_height; i++) {
						player.getWorld().getBlockAt(x, i, z).setType(random_material[r.nextInt(random_material.length)]);
					}
				}
			}.runTask(d);
		}
		return true;
	}

}
