package tomelo.cursed.customdata;

import org.bukkit.Material;

public class CustomLocation {
	public int x,y,z;
	public Material mat;
	public CustomLocation(int x, int y, int z, Material m) {
		this.x = x;
		this.y = y;
		this.z = z;
		mat = m;
	}
}
