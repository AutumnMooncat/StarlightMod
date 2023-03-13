package Starlight.augments;

import CardAugments.cardmods.AbstractAugment;
import Starlight.TheStarlightMod;
import Starlight.actions.ForetellAction;
import Starlight.cards.Augury;
import Starlight.cards.bookOfLight.DivineSense;
import Starlight.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ForetellMod extends AbstractAugment {
    public static final String ID = TheStarlightMod.makeID(ForetellMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    private static final int FORETELL = 1;
    private boolean setBaseVar;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card instanceof Augury || card instanceof DivineSense) {
            card.baseMagicNumber += FORETELL;
            card.magicNumber += FORETELL;
            setBaseVar = true;
        }
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        return block * MINOR_DEBUFF;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost != -2 && card.baseBlock > 1 && card.type == AbstractCard.CardType.SKILL;
    }

    @Override
    public String getPrefix() {
        return TEXT[0];
    }

    @Override
    public String getSuffix() {
        return TEXT[1];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (setBaseVar) {
            return rawDescription;
        }
        return rawDescription + String.format(TEXT[2], FORETELL);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!setBaseVar) {
            Wiz.atb(new ForetellAction(Wiz.adp().discardPile, FORETELL));
        }
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.COMMON;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ForetellMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
