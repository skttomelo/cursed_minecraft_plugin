package tomelo.cursed.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tomelo.cursed.customdata.CustomLocation;

public class CopyModel implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length != 7) {
				player.sendMessage("You messed up marking the locations");
				return false;
			}

			int[] x;
			int[] y;
			int[] z;
			
			ArrayList<CustomLocation> locations = new ArrayList<CustomLocation>();
			
			try {
				x = determineStartEnd(args[1],args[4]);
				y = determineStartEnd(args[2],args[5]);
				z = determineStartEnd(args[3],args[6]);
			}catch(Exception e) {
				player.sendMessage("PLEASE USE NUMBERS NOT LETTERS!");
				return false;
			}
			
			// gotta love this O(N^3) lol
			for(int starter_y = y[0], store_y = 0; starter_y < y[1]; starter_y++, store_y++) {
				for(int starter_x = x[0], store_x = 0; starter_x < x[1]; starter_x++, store_x++) {
					for(int starter_z = z[0], store_z = 0; starter_z < z[1]; starter_z++, store_z++) {
						
						locations.add(new CustomLocation(store_x,store_y,store_z,player.getWorld().getBlockAt(starter_x, starter_y, starter_z).getType()));
						
					}
				}
			}
			
			try {
				storeBuilding(locations, args[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			player.sendMessage("Done!");
		}
		
		return true;
	}
	
	private int[] determineStartEnd(String val0, String val1) {
		int x = Integer.parseInt(val0);
		int y = Integer.parseInt(val1);
		
		int[] vals = {y, x};
		if(x > y) {
			return vals;
		}
		vals[0] = x;
		vals[1] = y;
		
		return vals;
	}
	
	private void storeBuilding(ArrayList<CustomLocation> locs, String file_name) throws IOException {
		File f = new File("./plugins/cursed_assets/"+file_name+".txt");
		f.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		
		for(CustomLocation loc : locs) {
			writer.write(""+loc.x+","+loc.y+","+loc.z+","+loc.mat+"\n");
		}
		writer.close();
	}

}
