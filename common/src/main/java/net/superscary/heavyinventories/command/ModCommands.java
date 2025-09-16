package net.superscary.heavyinventories.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.superscary.heavyinventories.api.player.PlayerWeightCache;
import net.superscary.heavyinventories.api.weight.WeightCache;
import net.superscary.heavyinventories.api.weight.WeightOverride;

public class ModCommands {

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("heavyinventories")
                        .requires(source -> source.hasPermission(2)) // OP players only and above
                        .then(Commands.literal("set")
                                .then(Commands.literal("weight")
                                        .then(RequiredArgumentBuilder.<CommandSourceStack, Float>argument("weight_argument", FloatArgumentType.floatArg())
                                                .executes(context -> {

                                                    float number = FloatArgumentType.getFloat(context, "weight_argument");

                                                    if (context.getSource().getPlayer() == null) {
                                                        context.getSource().sendFailure(Component.translatable("chat.heavyinventories.command_set.failure", number));
                                                        return Command.SINGLE_SUCCESS;
                                                    }
                                                    ItemStack stack = context.getSource().getPlayer().getItemInHand(context.getSource().getPlayer().getUsedItemHand());
                                                    WeightOverride.put(stack.getItem(), number);
                                                    context.getSource().sendSuccess(() -> Component.translatable("chat.heavyinventories.command_set.success", number), true);
                                                    return Command.SINGLE_SUCCESS;
                                                })))
                                // TODO: Implement density calculation.
                                .then(Commands.literal("density")
                                        .then(RequiredArgumentBuilder.<CommandSourceStack, Float>argument("density_argument", FloatArgumentType.floatArg())
                                                .executes(context -> {
                                                    float number = FloatArgumentType.getFloat(context, "density_argument");
                                                    if (context.getSource().getPlayer() == null) {
                                                        context.getSource().sendFailure(Component.translatable("chat.heavyinventories.command_set.failure", number));
                                                        return Command.SINGLE_SUCCESS;
                                                    }
                                                    ItemStack stack = context.getSource().getPlayer().getItemInHand(context.getSource().getPlayer().getUsedItemHand());
                                                    //WeightOverride.put(stack.getItem(), number);
                                                    context.getSource().sendSuccess(() -> Component.translatable("chat.heavyinventories.command_set.success", number), true);
                                                    return Command.SINGLE_SUCCESS;
                                                })))
                                .then(Commands.literal("reload")
                                        .then(Commands.literal("weight")
                                                .executes(context -> {
                                                    WeightCache.clearAll();
                                                    return Command.SINGLE_SUCCESS;
                                                }))
                                        .then(Commands.literal("players")
                                                .executes(context -> {
                                                    PlayerWeightCache.clearAll();
                                                    return Command.SINGLE_SUCCESS;
                                                })))
                        ));
    }

}
