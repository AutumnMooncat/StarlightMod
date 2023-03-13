package Starlight.augments;

import CardAugments.cardmods.AbstractAugment;
import Starlight.TheStarlightMod;
import Starlight.actions.CleansePowerAction;
import Starlight.cards.bookOfLight.Esuna;
import Starlight.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;

public class CleanseMod extends AbstractAugment {
    public static final String ID = TheStarlightMod.makeID(CleanseMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    private static final int AMOUNT = 1;
    private boolean setBaseVar;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card instanceof Esuna) {
            card.baseMagicNumber += AMOUNT;
            card.magicNumber += AMOUNT;
            setBaseVar = true;
        }
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        return block * MODERATE_DEBUFF;
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
        return rawDescription + String.format(TEXT[2], AMOUNT);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!setBaseVar) {
            Wiz.atb(new VFXAction(new SanctityEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY)));
            Wiz.atb(new CleansePowerAction(Wiz.adp(), AMOUNT, c -> c.type == AbstractPower.PowerType.DEBUFF, powers -> {}));
        }
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.UNCOMMON;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CleanseMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
