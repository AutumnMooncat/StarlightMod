package Starlight.augments;

import CardAugments.cardmods.AbstractAugment;
import Starlight.TheStarlightMod;
import Starlight.cards.WandWhack;
import Starlight.powers.JinxPower;
import Starlight.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JinxMod extends AbstractAugment {
    public static final String ID = TheStarlightMod.makeID(JinxMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    private int damageComponent = 0;
    private boolean setBaseVar;


    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (damage > 1) {
            damageComponent = (int) Math.ceil(damage * (1 - HUGE_DEBUFF));
            return damage * HUGE_DEBUFF;
        }
        return damage;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (card instanceof WandWhack) {
            setBaseVar = true;
            return magic + damageComponent;
        }
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return TheStarlightMod.enableChimeraCrossover && card.cost != -2 && card.baseDamage > 1 && cardCheck(card, c -> usesEnemyTargeting(c));
    }

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0] + cardName + TEXT[1];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (setBaseVar) {
            return rawDescription;
        }
        return rawDescription + String.format(TEXT[2], damageComponent);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!setBaseVar) {
            this.addToBot(new ApplyPowerAction(target, Wiz.adp(), new JinxPower(target, damageComponent)));
        }
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.UNCOMMON;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new JinxMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
