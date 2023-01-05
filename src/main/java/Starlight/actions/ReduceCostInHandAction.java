package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.cards.interfaces.OnForetoldCard;
import Starlight.patches.CardCounterPatches;
import Starlight.powers.interfaces.OnForetellPower;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Predicate;

public class ReduceCostInHandAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("ReduceCost")).TEXT;
    private static final float DUR = Settings.ACTION_DUR_FASTER;
    private final Predicate<AbstractCard> filter;
    private final AbstractGameAction followUpAction;
    private final boolean anyNumber;

    public ReduceCostInHandAction() {
        this(1, c -> true, null);
    }

    public ReduceCostInHandAction(int amount) {
        this(amount, c -> true, null);
    }

    public ReduceCostInHandAction(int amount, Predicate<AbstractCard> filter) {
        this(amount, filter, null);
    }

    public ReduceCostInHandAction(int amount, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this(amount, false, filter, followUpAction);
    }

    public ReduceCostInHandAction(int amount, boolean anyNumber, Predicate<AbstractCard> filter, AbstractGameAction followUpAction) {
        this.amount = amount;
        this.filter = filter;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = DUR;
        this.followUpAction = followUpAction;
        this.anyNumber = anyNumber;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding() || amount == 0) {
            this.isDone = true;
        }
        ArrayList<AbstractCard> validCards = new ArrayList<>();
        for (AbstractCard c : Wiz.adp().hand.group) {
            if (filter.test(c) && (c.cost > 0 || c.costForTurn > 0)) {
                validCards.add(c);
            }
        }
        if (validCards.isEmpty()) {
            this.isDone = true;
            return;
        }

        if (amount >= validCards.size() && !anyNumber) {
            Collections.reverse(validCards);
            for (AbstractCard card : validCards) {
                triggerEffects(card);
            }
            if (this.followUpAction != null) {
                this.addToTop(this.followUpAction);
            }
        } else {
            Wiz.att(new SelectCardsInHandAction(this.amount, TEXT[0], anyNumber, anyNumber, validCards::contains, cards -> {
                for (AbstractCard c : cards) {
                    triggerEffects(c);
                }
                if (this.followUpAction != null) {
                    this.addToTop(this.followUpAction);
                }
            }));
        }
        this.isDone = true;
    }

    private static void triggerEffects(AbstractCard c) {
        c.cost = 0;
        c.costForTurn = 0;
        c.isCostModified = true;
        c.superFlash(Color.GOLD.cpy());
    }
}
