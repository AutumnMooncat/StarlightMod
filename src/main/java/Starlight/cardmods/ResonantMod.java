package Starlight.cardmods;

import Starlight.damageMods.ResonantDamage;
import Starlight.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class ResonantMod extends AbstractCardModifier {
    public static String ID = makeID("ResonantMod");
    private final boolean inherent;

    public ResonantMod(boolean inherent) {
        this.inherent = inherent;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        DamageModifierManager.addModifier(card, new ResonantDamage());
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        return block + (2 * Wiz.adp().hand.group.stream().filter(c -> c != card && CardModifierManager.hasModifier(c, ID)).count());
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + (2 * Wiz.adp().hand.group.stream().filter(c -> c != card && CardModifierManager.hasModifier(c, ID)).count());
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return inherent;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ResonantMod(inherent);
    }
}
