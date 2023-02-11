package Starlight.damageMods;

import Starlight.powers.ChillPower;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class FrostbiteDamage extends AbstractDamageModifier {
    final float multiplier;

    public FrostbiteDamage(float multiplier) {
        this.multiplier = multiplier;
    }


    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        if (target != null && target.hasPower(ChillPower.POWER_ID)) {
            return damage * multiplier;
        }
        return damage;
    }

    @Override
    public boolean isInherent() {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new FrostbiteDamage(multiplier);
    }
}
