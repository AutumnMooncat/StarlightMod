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

public class ProjectCardsInHandAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Project")).TEXT;
    public static final ArrayList<AbstractCard> projectedCards = new ArrayList<>();
    private AbstractGameAction followUpAction;

    public ProjectCardsInHandAction(int amount) {
        this(amount, null);
    }

    public ProjectCardsInHandAction(int amount, AbstractGameAction followUpAction) {
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
        if (validCards.size() == 1) {
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
            HashMap<AbstractCard, AbstractCard> copyMap = new HashMap<>();
            ArrayList<AbstractCard> selection = new ArrayList<>();
            for (AbstractCard c : validCards) {
                AbstractCard copy = c.makeStatEquivalentCopy();
                copyMap.put(copy, c);
                selection.add(copy);
            }
            Wiz.att(new BetterSelectCardsCenteredAction(selection, this.amount, amount == 1 ? TEXT[1] : TEXT[2] + amount + TEXT[3], cards -> {
                for (AbstractCard copy : cards) {
                    AbstractCard c = copyMap.get(copy);
                    if (AbstractDungeon.player.hoveredCard == c) {
                        AbstractDungeon.player.releaseCard();
                    }

                    AbstractDungeon.actionManager.removeFromQueue(c);
                    c.unhover();
                    c.untip();
                    //c.stopGlowing();
                    Wiz.adp().hand.group.remove(c);
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
