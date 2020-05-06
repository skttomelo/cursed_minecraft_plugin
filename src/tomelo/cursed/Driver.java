package tomelo.cursed;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Driver extends JavaPlugin{
	public void onEnable() {
		// create recipes
		createRecipes();
		
		// we attach our basic listener to the plugin manager
		getServer().getPluginManager().registerEvents(new BasicListener(this), this);
	}
	
	public void onDisable() {
		
	}
	
	public void createRecipes() {
		// pickaxe recipe
		ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE, 1);
		ShapedRecipe pickaxe_recipe = new ShapedRecipe(new NamespacedKey(this, "flintaxe"), pickaxe);
		
		pickaxe_recipe.shape(
				"AAA",
				"010",
				"010"
				);
		pickaxe_recipe.setIngredient('A', Material.FLINT);
		pickaxe_recipe.setIngredient('1', Material.STICK);
		
		getServer().addRecipe(pickaxe_recipe);
		
		// flint recipe
		ItemStack flint = new ItemStack(Material.FLINT, 1);
		ShapelessRecipe flint_recipe = new ShapelessRecipe(new NamespacedKey(this, "flinty"), flint);
		
		flint_recipe.addIngredient(1, Material.DIRT);
		
		getServer().addRecipe(flint_recipe);
		
		// pickaxe food recipe (NOTE: you have to use resource pack to change baked potato to look like iron pickaxe)
		ItemStack pickaxe_food = new ItemStack(Material.BAKED_POTATO, 1);
		ItemMeta im = pickaxe_food.getItemMeta();
		im.setDisplayName("хреновая кирка"); // apply cursed name
		pickaxe_food.setItemMeta(im);
		
		pickaxe_recipe = new ShapedRecipe(new NamespacedKey(this, "foodpickaxe"), pickaxe_food);
		
		pickaxe_recipe.shape(
				"0A0",
				"A1A",
				"010"
				);
		pickaxe_recipe.setIngredient('A', Material.IRON_INGOT);
		pickaxe_recipe.setIngredient('1', Material.STICK);
		
		getServer().addRecipe(pickaxe_recipe);
	}
}
