package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.powers.interfaces.OnGainEnergyPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FortunaPower extends AbstractPower implements OnGainEnergyPower {

    public static final String POWER_ID = TheStarlightMod.makeID(FortunaPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FortunaPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("panache");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onEnergyRecharge() {
        flash();
        Wiz.atb(new ApplyPowerAction(owner, owner, new ProvidencePower(owner, this.amount)));
    }

    @Override
    public void onGainEnergy(int amount) {
        flash();
        Wiz.atb(new ApplyPowerAction(owner, owner, new ProvidencePower(owner, this.amount)));
    }
}
