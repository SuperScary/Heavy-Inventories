package net.superscary.heavyinventories.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.superscary.heavyinventories.HeavyInventories;
import net.superscary.heavyinventories.api.player.PlayerWeightCache;
import net.superscary.heavyinventories.api.weight.CalculateWeight;
import net.superscary.heavyinventories.api.weight.WeightCache;
import net.superscary.heavyinventories.api.weight.WeightOverride;
import net.superscary.heavyinventories.helper.RegistryHelper;
import net.superscary.heavyinventories.platform.Services;

import java.util.concurrent.CompletableFuture;

public class ModCommands {

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("heavyinventories")
                        .requires(source -> source.hasPermission(2)) // OP players only and above
                        .then(Commands.literal("set")
                                .then(Commands.literal("weight")
                                        .then(RequiredArgumentBuilder.<CommandSourceStack, Float>argument("weight_argument", FloatArgumentType.floatArg())
                                                .executes(ModCommands::executeSetWeightCommand)
                                        )
                                )
                                // TODO: Implement density calculation.
                                .then(Commands.literal("density")
                                        .then(RequiredArgumentBuilder.<CommandSourceStack, Float>argument("density_argument", FloatArgumentType.floatArg())
                                                .executes(ModCommands::executeSetDensityCommand)
                                        )
                                )
                                .then(Commands.literal("reload")
                                        .then(Commands.literal("weight")
                                                .executes(context -> {
                                                    WeightCache.clearAll();
                                                    return Command.SINGLE_SUCCESS;
                                                })
                                        )
                                        .then(Commands.literal("players")
                                                .executes(context -> {
                                                    PlayerWeightCache.clearAll();
                                                    return Command.SINGLE_SUCCESS;
                                                })
                                        )
                                )
                        )
                        .then(Commands.literal("reload")
                                .executes(ModCommands::executeReloadCommand)
                        )
                        // TODO: Implement dump command. Should dump all items from the given modid into the paired json file.
                        .then(Commands.literal("dump")
                                .then(RequiredArgumentBuilder.<CommandSourceStack, String>argument("modid", StringArgumentType.string())
                                        .suggests(new ModidSuggestionProvider())
                                        .executes(ModCommands::executeDumpCommand)
                                )
                        )
        );
    }

    protected static int executeSetWeightCommand(CommandContext<CommandSourceStack> context) {
        float number = FloatArgumentType.getFloat(context, "weight_argument");

        if (context.getSource().getPlayer() == null) {
            context.getSource().sendFailure(Component.translatable("chat.heavyinventories.command_set.failure", number));
            return Command.SINGLE_SUCCESS;
        }
        ItemStack stack = context.getSource().getPlayer().getItemInHand(context.getSource().getPlayer().getUsedItemHand());
        WeightOverride.put(stack.getItem(), number);
        context.getSource().sendSuccess(() -> Component.translatable("chat.heavyinventories.command_set.success", number), true);
        return Command.SINGLE_SUCCESS;
    }

    protected static int executeSetDensityCommand(CommandContext<CommandSourceStack> context) {
        float number = FloatArgumentType.getFloat(context, "density_argument");
        if (context.getSource().getPlayer() == null) {
            context.getSource().sendFailure(Component.translatable("chat.heavyinventories.command_set.failure", number));
            return Command.SINGLE_SUCCESS;
        }
        ItemStack stack = context.getSource().getPlayer().getItemInHand(context.getSource().getPlayer().getUsedItemHand());
        //WeightOverride.put(stack.getItem(), number);
        context.getSource().sendSuccess(() -> Component.translatable("chat.heavyinventories.command_set.success", number), true);
        return Command.SINGLE_SUCCESS;
    }

    protected static int executeReloadCommand(CommandContext<CommandSourceStack> context) {
        WeightCache.clearAll();
        PlayerWeightCache.clearAll();
        CalculateWeight.clearRecursiveCache();
        return Command.SINGLE_SUCCESS;
    }

    protected static int executeDumpCommand(CommandContext<CommandSourceStack> context) {
        var modid = StringArgumentType.getString(context, "modid");

        if (!Services.PLATFORM.isModLoaded(modid)) {
            context.getSource().sendFailure(Component.literal(modid + " is invalid or not loaded!"));
            return Command.SINGLE_SUCCESS;
        }

        var items = RegistryHelper.getItemsFor(modid);
        var blocks = RegistryHelper.getBlocksFor(modid);

        context.getSource().sendSystemMessage(Component.literal("Dumping " + modid + "..."));
        context.getSource().sendSystemMessage(Component.literal("Found " + items.size() + " items and " + blocks.size() + " blocks."));
        
        var level = context.getSource().getLevel();
        WeightOverride.putDumpFile(items, blocks, level);

        context.getSource().sendSuccess(() -> Component.literal("Done!"), true);
        return Command.SINGLE_SUCCESS;
    }

    // Suggestion provider for the dump command.
    private static class ModidSuggestionProvider implements SuggestionProvider<CommandSourceStack> {

        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
            for (var modid : HeavyInventories.getInstance().getModIds()) {
                if (modid.equals(Services.PLATFORM.getPlatformName().toLowerCase())) continue;
                if (RegistryHelper.getItemsFor(modid).isEmpty() || RegistryHelper.getBlocksFor(modid).isEmpty()) continue;

                builder.suggest(modid);
            }

            return builder.buildFuture();
        }

    }

}
