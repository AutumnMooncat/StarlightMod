package Starlight.actions;

import Starlight.orbs.ProjectedCardOrb;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectCardAction extends AbstractGameAction {

    public ProjectCardAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        if (Wiz.adp().hand.isEmpty()) {
            this.isDone = true;
            return;
        }
        if (amount > Wiz.adp().hand.size()) {
            amount = Wiz.adp().hand.size();
        }
        if (amount == Wiz.adp().hand.size()) {
            for (AbstractCard c : Wiz.adp().hand.group) {
                if (AbstractDungeon.player.hoveredCard == c) {
                    AbstractDungeon.player.releaseCard();
                }

                AbstractDungeon.actionManager.removeFromQueue(c);
                c.unhover();
                c.untip();
                c.stopGlowing();
                ProjectedCardManager.addCard(c);
            }
            Wiz.adp().hand.clear();
        } else {
            HashMap<AbstractCard, AbstractCard> copyMap = new HashMap<>();
            ArrayList<AbstractCard> selection = new ArrayList<>();
            for (AbstractCard c : Wiz.adp().hand.group) {
                AbstractCard copy = c.makeStatEquivalentCopy();
                copyMap.put(copy, c);
                selection.add(copy);
            }
            Wiz.att(new BetterSelectCardsCenteredAction(selection, this.amount, "Blah Blah", cards -> {
                for (AbstractCard copy : cards) {
                    AbstractCard c = copyMap.get(copy);
                    if (AbstractDungeon.player.hoveredCard == c) {
                        AbstractDungeon.player.releaseCard();
                    }

                    AbstractDungeon.actionManager.removeFromQueue(c);
                    c.unhover();
                    c.untip();
                    c.stopGlowing();
                    Wiz.adp().hand.group.remove(c);
                    ProjectedCardManager.addCard(c);
                }
            }));
        }
        this.isDone = true;
    }
}
