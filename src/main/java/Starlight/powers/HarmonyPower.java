package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.interfaces.OnSwapPower;
import Starlight.powers.interfaces.OnTagTeamPower;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HarmonyPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(HarmonyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HarmonyPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("establishment");
        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card instanceof AbstractMagickCard) {
            return damage + amount;
        }
        return damage;
    }

    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        if (card instanceof AbstractMagickCard) {
            return blockAmount + amount;
        }
        return blockAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    /*@Override
    public void onTagTeam(AbstractCard card) {
        flash();
        Wiz.atb(new ApplyPowerAction(owner, owner, new ProvidencePower(owner, amount)));
    }*/

    /*@Override
    public void onSwap(boolean toPrim) {
        flash();
        Wiz.atb(new ApplyPowerAction(owner, owner, new ProvidencePower(owner, amount)));
    }*/
}
