package Starlight.actions;

import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ProjectSpecificCardAction extends AbstractGameAction {
    private final AbstractCard card;
    private final boolean isEndTurn;

    public ProjectSpecificCardAction(AbstractCard card) {
        this(card, false);
    }

    public ProjectSpecificCardAction(AbstractCard card, boolean isEndTurn) {
        this.card = card;
        this.isEndTurn = isEndTurn;
    }

    @Override
    public void update() {
        if (card == null) {
            this.isDone = true;
            return;
        }
        ProjectedCardManager.addCard(card, true, isEndTurn);
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
