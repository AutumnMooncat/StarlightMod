package Starlight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PlayRandomCardAction extends AbstractGameAction {
    private final Predicate<AbstractCard> filter;
    private final Consumer<ArrayList<AbstractCard>> callBack;
    private final CardGroup cardGroup;

    public PlayRandomCardAction(CardGroup cardGroup, int amount) {
        this(cardGroup, amount, c -> true, c -> {});
    }

    public PlayRandomCardAction(CardGroup cardGroup, int amount, Predicate<AbstractCard> filter) {
        this(cardGroup, amount, filter, c -> {});
    }

    public PlayRandomCardAction(CardGroup cardGroup, int amount, Predicate<AbstractCard> filter, Consumer<ArrayList<AbstractCard>> callBack) {
        this.amount = amount;
        this.cardGroup = cardGroup;
        this.filter = filter;
        this.callBack = callBack;
    }


    @Override
    public void update() {
        ArrayList<AbstractCard> playedCards = new ArrayList<>();
        if (cardGroup.isEmpty() || cardGroup.group.stream().noneMatch(filter)) {
            this.isDone = true;
            callBack.accept(playedCards);
            return;
        }
        CardGroup validCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : cardGroup.group) {
            if (filter.test(card)) {
                validCards.addToTop(card);
            }
        }
        if (amount > validCards.size()) {
            amount = validCards.size();
        }
        for (int i = 0 ; i < amount ; i++) {
            AbstractCard card = validCards.getRandomCard(true);
            cardGroup.group.remove(card);
            AbstractDungeon.getCurrRoom().souls.remove(card);
            AbstractDungeon.player.limbo.group.add(card);
            card.current_y = -200.0F * Settings.scale;
            card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            card.target_y = (float)Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.applyPowers();
            this.addToTop(new NewQueueCardAction(card, true, false, true));
            this.addToTop(new UnlimboAction(card));
            playedCards.add(card);
        }
        callBack.accept(playedCards);
        this.isDone = true;
    }
}
