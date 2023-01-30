package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProjectCopyInHandAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Project")).TEXT;
    public static final ArrayList<AbstractCard> projectedCards = new ArrayList<>();
    private final AbstractGameAction followUpAction;
    private final Predicate<AbstractCard> filter;

    private boolean isEndTurn;

    public ProjectCopyInHandAction(int amount) {
        this(amount, c -> true, null);
    }

    public ProjectCopyInHandAction(int amount, AbstractGameAction followUpAction) {
        this(amount, c -> true, followUpAction);
    }

    public ProjectCopyInHandAction(int amount, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this.amount = amount;
        this.followUpAction = followUpAction;
        this.filter = filter;
    }

    public ProjectCopyInHandAction(int amount, boolean isEndTurn, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this(amount, filter, followUpAction);
        this.isEndTurn = isEndTurn;
    }

    @Override
    public void update() {
        projectedCards.clear();
        if (Wiz.adp().hand.isEmpty()) {
            if (this.followUpAction != null) {
                this.addToTop(this.followUpAction);
            }
            this.isDone = true;
            return;
        }
        ArrayList<AbstractCard> validCards = Wiz.adp().hand.group.stream().filter(c -> c.cost != -2 && filter.test(c)).collect(Collectors.toCollection(ArrayList::new));
        if (validCards.isEmpty()) {
            this.isDone = true;
            return;
        }
        if (amount >= validCards.size()) {
            for (AbstractCard c : validCards) {
                AbstractCard copy = c.makeStatEquivalentCopy();
                /*if (AbstractDungeon.player.hoveredCard == c) {
                    AbstractDungeon.player.releaseCard();
                }

                AbstractDungeon.actionManager.removeFromQueue(c);
                c.unhover();
                c.untip();*/
                ProjectedCardManager.addCard(copy, true, isEndTurn);
                projectedCards.add(copy);
            }
            if (this.followUpAction != null) {
                this.addToTop(this.followUpAction);
            }
        } else {
            Wiz.att(new SelectCardsInHandAction(this.amount, TEXT[7], validCards::contains, cards -> {
                for (AbstractCard c : cards) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    ProjectedCardManager.addCard(copy, true, isEndTurn);
                    projectedCards.add(copy);
                }
                if (this.followUpAction != null) {
                    this.addToTop(this.followUpAction);
                }
            }));
        }
        this.isDone = true;
    }
}
