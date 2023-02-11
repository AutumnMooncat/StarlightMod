package Starlight.cardmods;

import Starlight.actions.RemoveCardModfierAction;
import Starlight.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Starlight.TheStarlightMod.makeID;

public class LanternMod extends AbstractCardModifier {
    public static String ID = makeID(LanternMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;
    private int amount;

    public LanternMod() {
        this(1);
    }

    public LanternMod(int amount) {
        this.amount = amount;
    }

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0]+cardName;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        StringBuilder s = new StringBuilder(TEXT[1]);
        if (amount <= 3) {
            for (int i = 0 ; i < amount ; i ++) {
                s.append(" [E]");
            }
            s.append(TEXT[2]);
        } else {
            s.append(" ").append(amount).append(" [E]").append(TEXT[2]);
        }
        return rawDescription + s;
    }

    @Override
    public void onDrawn(AbstractCard card) {
        card.superFlash();
        Wiz.att(new GainEnergyAction(amount));
        Wiz.atb(new RemoveCardModfierAction(card, this));
    }

    public boolean shouldApply(AbstractCard card) {
        if (CardModifierManager.hasModifier(card, ID)) {
            LanternMod lm = (LanternMod) CardModifierManager.getModifiers(card, ID).get(0);
            lm.amount += this.amount;
            card.initializeDescription();
            return false;
        }
        return true;
    }

    public AbstractCardModifier makeCopy() {
        return new LanternMod(amount);
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
