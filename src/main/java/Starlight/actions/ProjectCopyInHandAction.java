package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProjectCopyInHandAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Project")).TEXT;
    public static final ArrayList<AbstractCard> projectedCards = new ArrayList<>();
    private AbstractGameAction followUpAction;
    private Predicate<AbstractCard> filter;

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
        ArrayList<AbstractCard> copies = new ArrayList<>();
        for (AbstractCard c : validCards) {
            copies.add(c.makeStatEquivalentCopy());
        }
        if (amount >= copies.size()) {
            for (AbstractCard c : copies) {
                if (AbstractDungeon.player.hoveredCard == c) {
                    AbstractDungeon.player.releaseCard();
                }

                AbstractDungeon.actionManager.removeFromQueue(c);
                c.unhover();
                c.untip();
                ProjectedCardManager.addCard(c);
                projectedCards.add(c);
            }
            if (this.followUpAction != null) {
                this.addToTop(this.followUpAction);
            }
        } else {
            if (amount > validCards.size()) {
                amount = validCards.size();
            }
            Wiz.att(new BetterSelectCardsCenteredAction(copies, this.amount, amount == 1 ? TEXT[4] : TEXT[5] + amount + TEXT[6], cards -> {
                for (AbstractCard c : cards) {
                    if (AbstractDungeon.player.hoveredCard == c) {
                        AbstractDungeon.player.releaseCard();
                    }

                    AbstractDungeon.actionManager.removeFromQueue(c);
                    c.unhover();
                    c.untip();
                    ProjectedCardManager.addCard(c);
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
