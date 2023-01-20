package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.util.Wiz;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;
import java.util.function.Consumer;

public class FlowAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Flow")).TEXT;
    private final CardGroup hand = Wiz.adp().hand;
    private final Consumer<List<AbstractCard>> pickedCards;

    public FlowAction() {
        this(l -> {});
    }

    public FlowAction(Consumer<List<AbstractCard>> pickedCards) {
        this.pickedCards = pickedCards;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding() || hand.isEmpty()) {
            this.isDone = true;
        }

        Wiz.att(new BetterSelectCardsInHandAction(BaseMod.MAX_HAND_SIZE, TEXT[0], true, true, c -> true, cards -> {
            for (AbstractCard c : cards) {
                hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
            pickedCards.accept(cards);
            cards.clear(); // Remove from selection, so they don't get added back to hand
        }));

        this.isDone = true;
    }
}
