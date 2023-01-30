package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class ProjectTopCardAction extends AbstractGameAction {
    public static final ArrayList<AbstractCard> projectedCards = new ArrayList<>();
    private final AbstractGameAction followUpAction;
    private final boolean isEndTurn;

    public ProjectTopCardAction(int amount) {
        this(amount, false, null);
    }

    public ProjectTopCardAction(int amount, AbstractGameAction followUpAction) {
        this(amount, false, followUpAction);
    }

    public ProjectTopCardAction(int amount, boolean isEndTurn, AbstractGameAction followUpAction) {
        this.amount = amount;
        this.followUpAction = followUpAction;
        this.isEndTurn = isEndTurn;
    }

    @Override
    public void update() {
        projectedCards.clear();
        ArrayList<AbstractCard> validCards = Wiz.adp().drawPile.group.stream().filter(c -> c.cost != -2).collect(Collectors.toCollection(ArrayList::new));
        if (validCards.isEmpty()) {
            if (followUpAction != null) {
                Wiz.att(followUpAction);
            }
            this.isDone = true;
            return;
        }
        if (amount > validCards.size()) {
            amount = validCards.size();
        }
        Collections.reverse(validCards);
        ArrayList<AbstractCard> cards = new ArrayList<>();
        for (int i = 0 ; i < amount ; i++) {
            cards.add(validCards.get(i));
        }
        for (AbstractCard c : cards) {
            ProjectedCardManager.addCard(c, true, isEndTurn);
            Wiz.adp().drawPile.removeCard(c);
            projectedCards.add(c);
        }
        if (followUpAction != null) {
            Wiz.att(followUpAction);
        }
        this.isDone = true;
    }
}
