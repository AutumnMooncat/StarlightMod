package Starlight.damageMods;

import Starlight.powers.abilities.FiredUpPower;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class CrumpleDamage extends AbstractDamageModifier {
    int amount;

    public CrumpleDamage(int amount) {
        this.amount = amount;
    }

    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (lastDamageTaken > 0) {
            Wiz.atb(new ApplyPowerAction(target, info.owner, new FiredUpPower(target, amount)));
        }
    }

    @Override
    public boolean isInherent() {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new CrumpleDamage(amount);
    }
}
