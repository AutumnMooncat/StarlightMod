package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class ExhaustByPredAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Exhaust")).TEXT;
    private static final float DUR = Settings.ACTION_DUR_FASTER;
    private final CardGroup cardGroup;
    private final Predicate<AbstractCard> filter;
    private final AbstractGameAction followUpAction;
    public static final ArrayList<AbstractCard> exhaustedCards = new ArrayList<>();

    public ExhaustByPredAction(CardGroup cardGroup) {
        this(cardGroup, 1, c -> true, null);
    }

    public ExhaustByPredAction(CardGroup cardGroup, int amount) {
        this(cardGroup, amount, c -> true, null);
    }

    public ExhaustByPredAction(CardGroup cardGroup, int amount, Predicate<AbstractCard> filter) {
        this(cardGroup, amount, filter, null);
    }

    public ExhaustByPredAction(CardGroup cardGroup, int amount, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this.cardGroup = cardGroup;
        this.amount = amount;
        this.filter = filter;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DUR;
        this.followUpAction = followUpAction;
    }

    @Override
    public void update() {
        exhaustedCards.clear();
        if (AbstractDungeon.getCurrRoom().isBattleEnding() || amount == 0) {
            this.isDone = true;
        }
        ArrayList<AbstractCard> validCards = new ArrayList<>();
        for (AbstractCard c : cardGroup.group) {
            if (filter.test(c)) {
                validCards.add(c);
            }
        }
        if (validCards.isEmpty()) {
            this.isDone = true;
            return;
        }

        if (amount >= validCards.size()) {
            for (AbstractCard card : validCards) {
                cardGroup.moveToExhaustPile(card);
                exhaustedCards.add(card);
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
                    cardGroup.moveToExhaustPile(c);
                    exhaustedCards.add(c);
                }
                if (this.followUpAction != null) {
                    this.addToTop(this.followUpAction);
                }
            }));
        }
        this.isDone = true;
    }
}
