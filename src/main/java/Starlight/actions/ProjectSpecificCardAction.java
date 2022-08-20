package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ProjectSpecificCardAction extends AbstractGameAction {
    private AbstractCard card;

    public ProjectSpecificCardAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        if (card == null) {
            this.isDone = true;
            return;
        }
        ProjectedCardManager.addCard(card);
        if (Wiz.adp().drawPile.contains(card)) {
            Wiz.adp().drawPile.removeCard(card);
        }
        if (Wiz.adp().hand.contains(card)) {
            Wiz.adp().hand.removeCard(card);
        }
        if (Wiz.adp().discardPile.contains(card)) {
            Wiz.adp().discardPile.removeCard(card);
        }
        if (Wiz.adp().exhaustPile.contains(card)) {
            card.unfadeOut();
            Wiz.adp().exhaustPile.removeCard(card);
        }
        if (Wiz.adp().limbo.contains(card)) {
            Wiz.adp().limbo.removeCard(card);
        }
        this.isDone = true;
    }
}
