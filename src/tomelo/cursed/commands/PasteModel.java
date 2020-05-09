package tomelo.cursed.commands;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tomelo.cursed.customdata.CustomLocation;
import tomelo.cursed.main.Driver;

public class PasteModel implements CommandExecutor{
	Driver d;
	public PasteModel(Driver driver) {
		d = driver;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length != 1) {
				player.sendMessage("Please enter a file name.");
				return false;
			}
			File f = new File("./plugins/cursed_assets/"+args[0]+".txt");
			ArrayList<CustomLocation> locations = new ArrayList<CustomLocation>();
			try {
				Scanner reader = new Scanner(new FileReader(f));
				while(reader.hasNextLine()) {
					String line = reader.nextLine();
					String[] split = line.split(",");
					locations.add(new CustomLocation(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]),Material.getMaterial(split[3])));
				}
				reader.close();
			}catch(IOException e) {
				e.printStackTrace();
				player.sendMessage("The model you entered doesn't exist.");
				return false;
			}
			
			
			new BukkitRunnable() {
				public void run() {
					for(CustomLocation loc : locations) {
//						String material_name_below = player.getWorld().getBlockAt(player.getLocation().getBlockX()+loc.x, player.getLocation().getBlockY()+loc.y-1, player.getLocation().getBlockZ()+loc.z).getType().toString();
						Block b = player.getWorld().getBlockAt(player.getLocation().getBlockX()+loc.x, player.getLocation().getBlockY()+loc.y, player.getLocation().getBlockZ()+loc.z);
						// NOTE DOOR BLOCKS DO NOT PLACE PROPERLY						
						b.setType(loc.mat);
					}
				}
			}.runTask(d);
			
			
			player.sendMessage("Done!");
		}
		return true;
	}

}
