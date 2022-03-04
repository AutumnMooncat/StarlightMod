package Starlight.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Starlight.TheStarsAboveMod.makeID;

public class ExhaustMod extends AbstractCardModifier {
    public static String ID = makeID("ExhaustMod");
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + TEXT[0];
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.exhaust;
    }

    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
    }

    public AbstractCardModifier makeCopy() {
        return new ExhaustMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
