package Starlight.actions;

import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.function.Predicate;

public class BoostDamageInHandAction extends AbstractGameAction {
    private Predicate<AbstractCard> filter;

    public BoostDamageInHandAction(int amount) {
        this(amount, c -> true);
    }

    public BoostDamageInHandAction(int amount, Predicate<AbstractCard> filter) {
        this.amount = amount;
        this.filter = filter;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        boolean didSomething = false;
        for (AbstractCard c : Wiz.adp().hand.group) {
            if (c.baseDamage > 0 && filter.test(c)) {
                c.baseDamage += this.amount;
                c.superFlash();
                didSomething = true;
            }
        }
        if (didSomething) {
            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.3F);
        }
        this.isDone = true;
    }
}
