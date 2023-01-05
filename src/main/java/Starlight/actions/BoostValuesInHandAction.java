package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BoostValuesInHandAction extends AbstractGameAction {
    public enum StatBoost {
        DAMAGE,
        BLOCK,
        MAGIC
    }

    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Boost")).TEXT;
    private Predicate<AbstractCard> filter;
    private StatBoost stat;
    private int boost;

    public BoostValuesInHandAction(int cards, int boost, StatBoost stat) {
        this(cards, boost, stat, c -> true);
    }

    public BoostValuesInHandAction(int cards, int boost, StatBoost stat, Predicate<AbstractCard> filter) {
        this.amount = cards;
        this.boost = boost;
        this.stat = stat;
        this.actionType = ActionType.CARD_MANIPULATION;
        setFilter(filter);
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> validCards = Wiz.adp().hand.group.stream().filter(c -> c.cost != -2 && filter.test(c)).collect(Collectors.toCollection(ArrayList::new));
        if (validCards.isEmpty()) {
            this.isDone = true;
            return;
        }

        if (validCards.size() <= amount) {
            for (AbstractCard c : validCards) {
                switch (stat) {
                    case DAMAGE:
                        c.baseDamage += boost;
                        break;
                    case BLOCK:
                        c.baseBlock += boost;
                        break;
                    case MAGIC:
                        c.baseMagicNumber += boost;
                        c.magicNumber += boost;
                        break;
                }
                c.applyPowers();
                c.superFlash();
            }
            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.3F);
        } else {
            Wiz.att(new SelectCardsInHandAction(this.amount, TEXT[0], validCards::contains, cards -> {
                for (AbstractCard c : cards) {
                    switch (stat) {
                        case DAMAGE:
                            c.baseDamage += boost;
                            break;
                        case BLOCK:
                            c.baseBlock += boost;
                            break;
                        case MAGIC:
                            c.baseMagicNumber += boost;
                            c.magicNumber += boost;
                            break;
                    }
                    c.applyPowers();
                    c.superFlash();
                }
                CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.3F);
            }));
        }
        this.isDone = true;
    }

    private void setFilter(Predicate<AbstractCard> extraFilter) {
        switch (stat) {
            case DAMAGE:
                filter = extraFilter.and(c -> c.baseDamage > 0);
                break;
            case BLOCK:
                filter = extraFilter.and(c -> c.baseBlock > 0);
                break;
            case MAGIC:
                filter = extraFilter.and(c -> c.baseMagicNumber > 0);
                break;
        }
    }
}
