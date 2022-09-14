package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.cardmods.AscendedMod;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.interfaces.OnProvidenceActivatePower;
import Starlight.ui.ProjectedCardManager;
import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.SilentGainPowerEffect;

import java.util.ArrayList;

public class ProvidencePower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(ProvidencePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private float flashTimer;
    private boolean flashing;
    private final ArrayList<AbstractGameEffect> array;
    private int modTen;

    public ProvidencePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("nirvana");
        array = ReflectionHacks.getPrivateInherited(this, ProvidencePower.class, "effect");
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        //flashing = amount >= 10;
        checkStacks(this.amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        //flashing = amount >= 10;
        checkStacks(stackAmount);
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        //flashing = amount >= 10;
        checkStacks(reduceAmount);
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if (flashing) {
            flashTimer += Gdx.graphics.getDeltaTime();
            if (flashTimer > 1f) {
                array.add(new SilentGainPowerEffect(this));
                flashTimer = 0f;
            }
        }
    }

    /*@Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && amount >= 10) {
            AbstractDungeon.getCurrRoom().skipMonsterTurn = true;
            RetainBlockPatches.retainBlockThisTurn = true;
            amount -= 10;
            flashing = amount >= 10;
            if (amount <= 0) {
                Wiz.att(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
    }*/

    private void checkStacks(int recentlyAdded) {
        flashing = amount >= 10;
        if (recentlyAdded > 0) {
            modTen += recentlyAdded;
            while (modTen >= 10) {
                modTen -= 10;
                for (AbstractPower pow : owner.powers) {
                    if (pow instanceof OnProvidenceActivatePower) {
                        ((OnProvidenceActivatePower) pow).onProvidence();
                    }
                }
            }
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && this.amount >= 10 && card instanceof AbstractMagickCard && !ProjectedCardManager.ProjectedActionField.projectedField.get(action)) {
            CardModifierManager.addModifier(card, new AscendedMod());
            this.flash();
            this.amount -= 10;
            if (this.amount <= 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
            checkStacks(-10);
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}
