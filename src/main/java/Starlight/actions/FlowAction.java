package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import java.util.ArrayList;
import java.util.HashMap;
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

        HashMap<AbstractCard, AbstractCard> copyMap = new HashMap<>();
        ArrayList<AbstractCard> selection = new ArrayList<>();
        for (AbstractCard c : hand.group) {
            AbstractCard copy = c.makeStatEquivalentCopy();
            copyMap.put(copy, c);
            selection.add(copy);
        }

        Wiz.att(new BetterSelectCardsCenteredAction(selection, 99, TEXT[1], true, c -> true, cards -> {
            for (AbstractCard copy : cards) {
                AbstractCard c = copyMap.get(copy);
                hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
            if (!cards.isEmpty()) {
                Wiz.applyToSelf(new DrawCardNextTurnPower(Wiz.adp(), cards.size()));
            }
            pickedCards.accept(cards);
        }));

        this.isDone = true;
    }
}
