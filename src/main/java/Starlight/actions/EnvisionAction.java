package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.ui.EnvisionedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EnvisionAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Envision")).TEXT;
    private final Consumer<List<AbstractCard>> pickedCards;
    private final boolean anyNumber;
    private int toPick;

    public EnvisionAction(int amount) {
        this(amount, 0, false, c -> {});
    }

    public EnvisionAction(int amount, int amountToPick, Consumer<List<AbstractCard>> pickedCards) {
        this(amount, amountToPick, false, pickedCards);
    }

    public EnvisionAction(int amount, int amountToPick, boolean anyNumber, Consumer<List<AbstractCard>> pickedCards) {
        this.amount = amount;
        this.toPick = amountToPick;
        this.anyNumber = anyNumber;
        this.pickedCards = pickedCards;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> validCards = getNonEnvisionedCards();
        if (validCards.size() < amount && Wiz.adp().discardPile.size() > 0) {
            Wiz.att(new EnvisionAction(amount, toPick, anyNumber, pickedCards));
            Wiz.att(new ReshuffleNCardsAction(amount -validCards.size()));
        } else {
            if (amount > validCards.size()) {
                amount = validCards.size();
            }
            for (int i = 0 ; i < amount; i++) {
                EnvisionedCardManager.addCard(validCards.get(validCards.size() - 1 - i));
            }
            if (toPick > EnvisionedCardManager.cards.size()) {
                toPick = EnvisionedCardManager.cards.size();
            }
            if (toPick > 0) {
                ArrayList<AbstractCard> copies = new ArrayList<>();
                HashMap<AbstractCard, AbstractCard> copyMap = new HashMap<>();
                for (AbstractCard c : EnvisionedCardManager.cards.group) {
                    AbstractCard copy = c.makeStatEquivalentCopy();
                    copies.add(copy);
                    copyMap.put(copy, c);
                }
                Wiz.att(new BetterSelectCardsCenteredAction(copies, toPick, toPick == 1 ? TEXT[1] : TEXT[2] + toPick + TEXT[3], anyNumber, c -> true, copyCards -> {
                    ArrayList<AbstractCard> chosenCards = new ArrayList<>();
                    for (AbstractCard copy : copyCards) {
                        chosenCards.add(copyMap.get(copy));
                    }
                    pickedCards.accept(chosenCards);
                    copies.clear();
                    copyMap.clear();
                    chosenCards.clear();
                }));
            }
        }
        this.isDone = true;
    }

    public static ArrayList<AbstractCard> getNonEnvisionedCards() {
        return Wiz.adp().drawPile.group.stream().filter(c -> !EnvisionedCardManager.cards.contains(c)).collect(Collectors.toCollection(ArrayList::new));
    }

    /*@Override
    public void update() {
        if (amount == -1) {
            amount = calcNextTurnHandSize();
        }
        if (Wiz.adp().drawPile.size() < amount && Wiz.adp().discardPile.size() > 0) {
            Wiz.att(new EnvisionAction(amount, toPick, anyNumber, pickedCards, unpickedCards));
            EmptyDeckShuffleAction e = new EmptyDeckShuffleAction();
            ShuffleSplicePatch.SpliceField.splice.set(e, true);
            Wiz.att(e);
        } else {
            ArrayList<AbstractCard> cards = new ArrayList<>();
            if (amount > Wiz.adp().drawPile.size()) {
                amount = Wiz.adp().drawPile.size();
            }
            if (toPick > amount) {
                toPick = amount;
            }
            for (int i = 0 ; i < amount; i++) {
                cards.add(Wiz.adp().drawPile.getNCardFromTop(i));
            }
            Wiz.att(new BetterSelectCardsCenteredAction(cards, toPick, toPick == 1 ? TEXT[1] : TEXT[2] + toPick + TEXT[3], anyNumber, c -> true, picked -> {
                for (AbstractCard c : cards) {
                    EnvisionedCardManager.addCard(c);
                }
                cards.removeAll(picked);
                pickedCards.accept(picked);
                unpickedCards.accept(cards);
            }));
        }
        this.isDone = true;
    }*/

    /*public static int calcNextTurnHandSize() {
        int size = Wiz.adp().gameHandSize;
        for (AbstractPower p : Wiz.adp().powers) {
            if (p instanceof DrawCardNextTurnPower) {
                size += p.amount;
            }
            if (p instanceof DrawReductionPower) {
                size -= p.amount;
            }
        }
        size = Math.min(size, BaseMod.MAX_HAND_SIZE - predictRetainedCards());
        return size;
    }

    public static int predictRetainedCards() {
        int retained = 0;
        for (AbstractCard c : Wiz.adp().hand.group) {
            if (c.selfRetain || c.retain || (!c.isEthereal && (Wiz.adp().hasRelic(RunicPyramid.ID) || Wiz.adp().hasPower(EquilibriumPower.POWER_ID)))) {
                retained++;
            }
        }
        return retained;
    }*/
}
