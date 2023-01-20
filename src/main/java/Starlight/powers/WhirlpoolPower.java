package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.powers.interfaces.OnManualDiscardPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WhirlpoolPower extends AbstractPower implements OnManualDiscardPower {

    public static final String POWER_ID = TheStarlightMod.makeID(WhirlpoolPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WhirlpoolPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("amplify");
        updateDescription();
    }

/*    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        Wiz.forAllMonstersLiving(mon -> {
            if (mon.hasPower(WetPower.POWER_ID)) {
                Wiz.atb(new DamageAction(mon, new DamageInfo(null, amount * mon.getPower(WetPower.POWER_ID).amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
            }
        });
    }*/

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onManualDiscard() {
        flash();
        Wiz.atb(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
    }
}
