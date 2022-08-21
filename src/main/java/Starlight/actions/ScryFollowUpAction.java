package Starlight.actions;

import Starlight.patches.CountScryCardsPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ScryFollowUpAction extends AbstractGameAction {
    public Consumer<ArrayList<AbstractCard>> callBack;

    public ScryFollowUpAction(Consumer<ArrayList<AbstractCard>> callBack) {
        this.callBack = callBack;
    }

    @Override
    public void update() {
        callBack.accept(CountScryCardsPatch.scriedCards());
        this.isDone = true;
    }
}
