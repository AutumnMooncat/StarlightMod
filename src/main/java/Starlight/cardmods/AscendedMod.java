package Starlight.cardmods;

import Starlight.actions.ProjectSpecificCardAction;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class AscendedMod extends AbstractCardModifier {
    public static String ID = makeID(AscendedMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0]+cardName+TEXT[1];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + TEXT[2];
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.tags.contains(CustomTags.ASCENDED);
    }

    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.ASCENDED);
    }

    public AbstractCardModifier makeCopy() {
        return new AscendedMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
