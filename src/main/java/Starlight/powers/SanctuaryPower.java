package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.actions.DoIfPowerAppliedAction;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SanctuaryPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(SanctuaryPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SanctuaryPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("devotion");
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (source == this.owner && target != this.owner && power.type == PowerType.DEBUFF) {
            flash();
            Wiz.att(new DoIfPowerAppliedAction(power, new ApplyPowerAction(Wiz.adp(), Wiz.adp(), new BarbPower(Wiz.adp(), amount))));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
