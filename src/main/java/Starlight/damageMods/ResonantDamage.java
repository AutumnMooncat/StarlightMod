package Starlight.damageMods;

import Starlight.TheStarlightMod;
import Starlight.util.Wiz;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;

public class ResonantDamage extends AbstractDamageModifier {
    public static final String ID = TheStarlightMod.makeID(ResonantDamage.class.getSimpleName());
    public static final CardStrings STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static ArrayList<TooltipInfo> info;

    /*@Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        return damage + (2 * Wiz.adp().hand.group.stream().filter(c -> c != card && DamageModifierManager.modifiers(c).stream().anyMatch(m -> m instanceof ResonantDamage)).count());
    }*/

    @Override
    public String getCardDescriptor() {
        return STRINGS.NAME;
    }

    @Override
    public ArrayList<TooltipInfo> getCustomTooltips() {
        if (info == null) {
            info = new ArrayList<>();
            info.add(new TooltipInfo(STRINGS.NAME, STRINGS.DESCRIPTION));
        }
        return info;
    }

    @Override
    public boolean isInherent() {
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new ResonantDamage();
    }
}
