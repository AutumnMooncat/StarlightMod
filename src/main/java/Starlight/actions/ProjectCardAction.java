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

public class ProjectCardAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Project")).TEXT;

    public ProjectCardAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        if (Wiz.adp().hand.isEmpty()) {
            this.isDone = true;
            return;
        }
        ArrayList<AbstractCard> validCards = Wiz.adp().hand.group.stream().filter(c -> c.cost != -2).collect(Collectors.toCollection(ArrayList::new));
        if (amount >= validCards.size()) {
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
                }
            }));
        }
        this.isDone = true;
    }
}
