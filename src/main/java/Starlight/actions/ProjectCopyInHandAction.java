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
import java.util.stream.Collectors;

public class ProjectCopyInHandAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Project")).TEXT;
    public static final ArrayList<AbstractCard> projectedCards = new ArrayList<>();
    private AbstractGameAction followUpAction;

    public ProjectCopyInHandAction(int amount) {
        this(amount, null);
    }

    public ProjectCopyInHandAction(int amount, AbstractGameAction followUpAction) {
        this.amount = amount;
        this.followUpAction = followUpAction;
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
        ArrayList<AbstractCard> validCards = Wiz.adp().hand.group.stream().filter(c -> c.cost != -2).collect(Collectors.toCollection(ArrayList::new));
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
            Wiz.att(new BetterSelectCardsCenteredAction(copies, this.amount, amount == 1 ? TEXT[1] : TEXT[2] + amount + TEXT[3], cards -> {
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
