package superscary.heavyinventories.data.player.enc;

import net.minecraft.world.entity.player.Player;
import superscary.heavyinventories.config.CommonConfig;
import superscary.heavyinventories.data.player.ModDataAttachments;
import superscary.heavyinventories.data.player.PlayerWeight;

public record EncumbranceManager(Player player, PlayerWeight weight) {

    /**
     * Sets settings on player based on current weight
     */
    public void delegate () {
        setEncumbered();
        setOverEncumbered();
    }

    private void setEncumbered () {
        player().setData(ModDataAttachments.ENCUMBERED, new Encumbered(isEncumbered()));
    }

    private void setOverEncumbered () {
        player().setData(ModDataAttachments.OVER_ENCUMBERED, new OverEncumbered(isOverEncumbered()));
    }

    /**
     * Dictates if player is carrying too much but able to move still
     *
     * @return {@link Boolean}
     */
    private boolean isEncumbered () {
        return ((weight().maxWeight() - weight().currentWeight()) * 100) / 100 >= CommonConfig.encumberedPercentage;
    }

    /**
     * Dictates if player is carrying too much and unable to move
     *
     * @return {@link Boolean}
     */
    private boolean isOverEncumbered () {
        return weight().maxWeight() <= weight().currentWeight();
    }

}
