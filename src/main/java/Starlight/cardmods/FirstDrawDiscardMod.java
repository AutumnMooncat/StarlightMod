package Starlight.cardmods;

import Starlight.TheStarlightMod;
import Starlight.actions.RemoveCardModfierAction;
import Starlight.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class FirstDrawDiscardMod extends AbstractCardModifier {
    public static final String ID = TheStarlightMod.makeID(FirstDrawDiscardMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + TEXT[0];
    }

    public void onDrawn(AbstractCard card) {
        card.superFlash();
        Wiz.atb(new DiscardSpecificCardAction(card));
        Wiz.atb(new RemoveCardModfierAction(card, this, true));
    }
    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FirstDrawDiscardMod();
    }
}
