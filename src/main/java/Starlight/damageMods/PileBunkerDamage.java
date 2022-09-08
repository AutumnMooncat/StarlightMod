package Starlight.damageMods;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class PileBunkerDamage extends AbstractDamageModifier {
    int amount;

    public PileBunkerDamage(int amount) {
        this.amount = amount;
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        if (target.currentBlock > 0) {
            return damage * (1 + amount/100f);
        }
        return damage;
    }

    @Override
    public boolean isInherent() {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new PileBunkerDamage(amount);
    }
}
