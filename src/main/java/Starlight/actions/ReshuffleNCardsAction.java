package Starlight.actions;

import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ReshuffleNCardsAction extends AbstractGameAction {
    private boolean vfxDone = false;
    private int count;

    public ReshuffleNCardsAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        if (!this.vfxDone) {
            if (count < amount && !Wiz.adp().discardPile.isEmpty()) {
                AbstractCard c = Wiz.adp().drawPile.getRandomCard(true);
                Wiz.adp().discardPile.removeCard(c);
                AbstractDungeon.getCurrRoom().souls.shuffle(c, false);
                ++count;
                return;
            }

            this.vfxDone = true;
        }

        this.isDone = true;
    }
}
