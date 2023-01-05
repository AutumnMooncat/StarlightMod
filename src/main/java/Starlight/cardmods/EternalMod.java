package Starlight.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Starlight.TheStarlightMod.makeID;

public class EternalMod extends AbstractCardModifier {
    public static String ID = makeID(EternalMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return TEXT[0] + rawDescription;
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    public AbstractCardModifier makeCopy() {
        return new EternalMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
