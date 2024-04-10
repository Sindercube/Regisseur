package com.sindercube.regisseur.api.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface BloodMoonCallback {

	Event<BloodMoonCallback> EVENT = EventFactory.createArrayBacked(BloodMoonCallback.class, listeners ->
			(world, state) -> {
				for (BloodMoonCallback event : listeners) {
					ActionResult result = event.interact(world, state);
					if (result != ActionResult.PASS) return result;
				}
				return ActionResult.PASS;
			}
	);

	ActionResult interact(World world, boolean state);

}
