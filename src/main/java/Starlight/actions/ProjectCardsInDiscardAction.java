package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProjectCardsInDiscardAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Project")).TEXT;
    public static final ArrayList<AbstractCard> projectedCards = new ArrayList<>();
    private final AbstractGameAction followUpAction;
    private final Predicate<AbstractCard> filter;
    private final boolean anyAmount;
    private boolean isEndTurn;

    public ProjectCardsInDiscardAction(int amount) {
        this(amount, null);
    }

    public ProjectCardsInDiscardAction(int amount, AbstractGameAction followUpAction) {
        this(amount, c -> true, followUpAction);
    }

    public ProjectCardsInDiscardAction(int amount, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this(amount, false, filter, followUpAction);
    }

    public ProjectCardsInDiscardAction(int amount, boolean anyAmount, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this.amount = amount;
        this.followUpAction = followUpAction;
        this.filter = filter;
        this.anyAmount = anyAmount;
    }

    public ProjectCardsInDiscardAction(int amount, boolean anyAmount, boolean isEndTurn, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this(amount, anyAmount, filter, followUpAction);
        this.isEndTurn = isEndTurn;
    }

    @Override
    public void update() {
        projectedCards.clear();
        if (Wiz.adp().discardPile.isEmpty()) {
            if (this.followUpAction != null) {
                this.addToTop(this.followUpAction);
            }
            this.isDone = true;
            return;
        }
        ArrayList<AbstractCard> validCards = Wiz.adp().discardPile.group.stream().filter(c -> c.cost != -2 && filter.test(c)).collect(Collectors.toCollection(ArrayList::new));
        if (validCards.isEmpty()) {
            this.isDone = true;
            return;
        }
        if (validCards.size() == 1 && !anyAmount) {
            for (AbstractCard c : validCards) {
                if (AbstractDungeon.player.hoveredCard == c) {
                    AbstractDungeon.player.releaseCard();
                }

                AbstractDungeon.actionManager.removeFromQueue(c);
                c.unhover();
                c.untip();
                //c.stopGlowing();
                ProjectedCardManager.addCard(c, true, isEndTurn);
                Wiz.adp().discardPile.removeCard(c);
                projectedCards.add(c);
            }
            if (this.followUpAction != null) {
                this.addToTop(this.followUpAction);
            }
        } else {
            if (amount > validCards.size()) {
                amount = validCards.size();
            }
            Wiz.att(new BetterSelectCardsCenteredAction(validCards, this.amount, amount == 1 ? TEXT[1] : TEXT[2] + amount + TEXT[3], anyAmount, true, cards -> {
                for (AbstractCard c : cards) {
                    if (AbstractDungeon.player.hoveredCard == c) {
                        AbstractDungeon.player.releaseCard();
                    }

                    AbstractDungeon.actionManager.removeFromQueue(c);
                    c.unhover();
                    c.untip();
                    c.lighten(false);
                    //c.stopGlowing();
                    Wiz.adp().discardPile.group.remove(c);
                    ProjectedCardManager.addCard(c, true, isEndTurn);
                    projectedCards.add(c);
                }
                if (this.followUpAction != null) {
                    this.addToTop(this.followUpAction);
                }
            }));
        }
        this.isDone = true;
    }
}
