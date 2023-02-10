package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.powers.interfaces.OnManualDiscardPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SoakPower extends AbstractPower implements OnManualDiscardPower {

    public static final String POWER_ID = TheStarlightMod.makeID(SoakPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean prim;
    private boolean applied;

    public SoakPower(AbstractCreature owner, int amount, boolean prim) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.prim = prim;
        this.loadRegion("like_water");
        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        applied = false;
    }

    /*@Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (isActive() && target != owner && !applied && target.hasPower(WetPower.POWER_ID) && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            Wiz.atb(new DrawCardAction(amount));
            applied = true;
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

    /*@Override
    public boolean shouldRender(AbstractCard card) {
        return isActive() && card.type == AbstractCard.CardType.ATTACK && !applied && AbstractDungeon.getMonsters().monsters.stream().anyMatch(m -> !m.isDeadOrEscaped() && m.hasPower(WetPower.POWER_ID));
    }*/

    @Override
    public void onManualDiscard() {
        if (isActive() && !applied) {
            flash();
            Wiz.atb(new DrawCardAction(amount));
            applied = true;
        }
    }
}
