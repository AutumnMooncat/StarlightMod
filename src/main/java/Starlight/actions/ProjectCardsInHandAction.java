package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProjectCardsInHandAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Project")).TEXT;
    public static final ArrayList<AbstractCard> projectedCards = new ArrayList<>();
    private final AbstractGameAction followUpAction;
    private Predicate<AbstractCard> filter;
    private boolean anyAmount;

    public ProjectCardsInHandAction(int amount) {
        this(amount, null);
    }

    public ProjectCardsInHandAction(int amount, AbstractGameAction followUpAction) {
        this(amount, c -> true, followUpAction);
    }

    public ProjectCardsInHandAction(int amount, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this(amount, false, filter, followUpAction);
    }

    public ProjectCardsInHandAction(int amount, boolean anyAmount, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this.amount = amount;
        this.followUpAction = followUpAction;
        this.filter = filter;
        this.anyAmount = anyAmount;
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
        if (validCards.size() == 1 && !anyAmount) {
            for (AbstractCard c : validCards) {
                if (AbstractDungeon.player.hoveredCard == c) {
                    AbstractDungeon.player.releaseCard();
                }

                AbstractDungeon.actionManager.removeFromQueue(c);
                c.unhover();
                c.untip();
                //c.stopGlowing();
                ProjectedCardManager.addCard(c);
                Wiz.adp().hand.removeCard(c);
                projectedCards.add(c);
            }
            if (this.followUpAction != null) {
                this.addToTop(this.followUpAction);
            }
        } else {
            if (amount > validCards.size()) {
                amount = validCards.size();
            }
            Wiz.att(new BetterSelectCardsInHandAction(this.amount, TEXT[0], anyAmount, anyAmount, validCards::contains, cards -> {
                for (AbstractCard c : cards) {
                    if (AbstractDungeon.player.hoveredCard == c) {
                        AbstractDungeon.player.releaseCard();
                    }

                    AbstractDungeon.actionManager.removeFromQueue(c);
                    c.unhover();
                    c.untip();
                    //c.stopGlowing();
                    //Wiz.adp().hand.group.remove(c);
                    ProjectedCardManager.addCard(c);
                    projectedCards.add(c);
                }
                cards.clear(); // Remove from selection, so they don't get added back to hand
                if (this.followUpAction != null) {
                    this.addToTop(this.followUpAction);
                }
            }));
        }
        this.isDone = true;
    }
}
