package Starlight.actions;

import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.function.Predicate;

public class ScaleByPredAction extends AbstractGameAction {
    public enum StatBoost {
        DAMAGE,
        BLOCK,
        MAGIC
    }

    private final AbstractCard card;
    private final Predicate<AbstractCard> pred;
    private final StatBoost stat;

    public ScaleByPredAction(AbstractCard card, int amount, StatBoost stat, Predicate<AbstractCard> pred) {
        this.card = card;
        this.amount = amount;
        this.pred = pred;
        this.stat = stat;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.add(card);
        cards.addAll(Wiz.adp().drawPile.group);
        cards.addAll(Wiz.adp().hand.group);
        cards.addAll(Wiz.adp().discardPile.group);
        cards.addAll(ProjectedCardManager.cards.group);

        for (AbstractCard c : cards) {
            if (pred.test(c)) {
                switch (stat) {
                    case DAMAGE:
                        card.baseDamage += amount;
                        break;
                    case BLOCK:
                        card.baseBlock += amount;
                        break;
                    case MAGIC:
                        card.baseMagicNumber += amount;
                        card.magicNumber += amount;
                        break;
                }
                card.applyPowers();
            }
        }

        this.isDone = true;
    }
}
