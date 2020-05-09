package tomelo.cursed.main;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import tomelo.cursed.commands.CopyModel;
import tomelo.cursed.commands.Mound;
import tomelo.cursed.commands.PasteModel;
import tomelo.cursed.listeners.BasicListener;
import tomelo.cursed.listeners.FarmListener;

public class Driver extends JavaPlugin{
	public void onEnable() {
		//create assets folder
		new File("./plugins/cursed_assets").mkdir();
		
		// create recipes
		createRecipes();
		
		// add commands to the plugin
		getCommand("copymodel").setExecutor(new CopyModel());
		getCommand("pastemodel").setExecutor(new PasteModel(this));
		getCommand("mound").setExecutor(new Mound(this));
		
		// we attach our basic listener to the plugin manager
		getServer().getPluginManager().registerEvents(new BasicListener(this), this);
		getServer().getPluginManager().registerEvents(new FarmListener(this), this);
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
		im.setDisplayName("хренова�? кирка"); // apply cursed name (shitty pickaxe, but it spells Horseradish Pickaxe)
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
		
		// stone axe recipe (NOTE: uses resource pack to mask rotten flesh)
		ItemStack axe_food = new ItemStack(Material.ROTTEN_FLESH, 1);
		im = axe_food.getItemMeta();
		im.setDisplayName("烂石斧"); // apply cursed name (shitty stone axe, but it spells Rotten Stone Axe)
		axe_food.setItemMeta(im);
		
		ShapedRecipe axe_recipe = new ShapedRecipe(new NamespacedKey(this, "foodaxe"), axe_food);
		
		axe_recipe.shape(
				"AA0",
				"A10",
				"010"
				);
		axe_recipe.setIngredient('A', Material.FLINT);
		axe_recipe.setIngredient('1', Material.STICK);
		
		getServer().addRecipe(axe_recipe);
	}
}
