package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExampleMod implements ModInitializer {
	public static BlockEntityType<ClockworkBlockEntity> CLOCKWORK_BLOCK_ENTITY;
	public static Block SPINNY_WHEEL = new ClockworkBlock();
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.BLOCK, new Identifier("spinny_wheel", "spinny_wheel"), SPINNY_WHEEL);
		CLOCKWORK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("spinny_wheel", "spinny_wheel_block_entity"), FabricBlockEntityTypeBuilder.create(ClockworkBlockEntity::new, SPINNY_WHEEL).build(null));
		Registry.register(Registry.ITEM, new Identifier("spinny_wheel"), new BlockItem(SPINNY_WHEEL, new FabricItemSettings().group(ItemGroup.MISC)));
		//System.out.println("Hello Fabric world!");
	}
}
