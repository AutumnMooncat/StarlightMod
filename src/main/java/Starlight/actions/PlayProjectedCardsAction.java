package Starlight.actions;

import Starlight.ui.ProjectedCardManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class PlayProjectedCardsAction extends AbstractGameAction {

    public PlayProjectedCardsAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        ProjectedCardManager.playCards();
        this.isDone = true;
    }
}
