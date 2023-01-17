package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.powers.DelayedDamagePower;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FrameSkipPower extends AbstractPower /*implements RenderOnCardPower, OnSwapPower*/ {

    public static final String POWER_ID = TheStarlightMod.makeID(FrameSkipPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean prim;
    private boolean applied;
    private int stored;

    public FrameSkipPower(AbstractCreature owner, int amount, boolean prim) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.prim = prim;
        this.loadRegion("amplify");
        updateDescription();
    }

    //TODO - rework to something less OP?
    // Ideas:
    // Some damage negated each turn? Could lower like Tungsten Rod, or carry over to next turn like Snail Shell
    /*@Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (isActive() && (card.exhaust || action.exhaustCard) && !card.purgeOnUse) {
            flash();
            Wiz.atb(new DrawCardAction(amount));
        }
    }*/

    @Override
    public void atStartOfTurn() {
        stored = 0;
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && amount - stored > 0 && isActive()) {
            int negated = Math.min(amount - stored, damageAmount);
            stored += negated;
            flash();
            Wiz.att(new ApplyPowerAction(owner, owner, new DelayedDamagePower(owner, negated)));
            return damageAmount - negated;
        }
        return damageAmount;
    }

    private boolean isActive() {
        return owner instanceof StarlightSisters && ((StarlightSisters) owner).attackerInFront == prim;
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(stored), x, y + 15.0F * Settings.scale, this.fontScale, c);
        super.renderAmount(sb, x, y, c);
    }

    @Override
    public void updateDescription() {
        if (prim) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
        /*if (amount == 1) {
            if (prim) {
                this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
            } else {
                this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
            }
        } else {
            if (prim) {
                this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[3];
            } else {
                this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[3];
            }
        }*/
    }

    /*@Override
    public boolean shouldRender(AbstractCard card) {
        return isActive() && card.hasTag(CustomTags.STARLIGHT_SWAPS);
    }

    @Override
    public void onSwap(boolean toPrim) {
        if (!isActive()) {
            flash();
            Wiz.atb(new DrawCardAction(amount));
        }
    }*/
}
