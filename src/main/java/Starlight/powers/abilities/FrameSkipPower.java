package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.powers.interfaces.OnSwapPower;
import Starlight.powers.interfaces.RenderOnCardPower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FrameSkipPower extends AbstractPower implements RenderOnCardPower, OnSwapPower {

    public static final String POWER_ID = TheStarlightMod.makeID(FrameSkipPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean prim;
    private boolean applied;

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

    // TODO - rework to something less OP?
    /*@Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (isActive() && (card.exhaust || action.exhaustCard) && !card.purgeOnUse) {
            flash();
            Wiz.atb(new DrawCardAction(amount));
        }
    }*/

    private boolean isActive() {
        return owner instanceof StarlightSisters && ((StarlightSisters) owner).attackerInFront == prim;
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
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
        }
    }

    @Override
    public boolean shouldRender(AbstractCard card) {
        return isActive() && card.hasTag(CustomTags.STARLIGHT_SWAPS);
    }

    @Override
    public void onSwap(boolean toPrim) {
        if (!isActive()) {
            flash();
            Wiz.atb(new DrawCardAction(amount));
        }
    }
}
