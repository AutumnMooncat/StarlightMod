package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ChillPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(ChillPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final int BASE_PERCENT = 20;

    public ChillPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.priority = 98;
        this.isTurnBased = true;
        this.loadRegion("blur");
        updateDescription();
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 0.8f;
        }
        return damage;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * (1f + getDamageBoost()/100f);
        }
        return damage;
    }

    public int getDamageBoost() {
        int boost = BASE_PERCENT;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            AbstractPower p = m.getPower(FrostbitePower.POWER_ID);
            if (p != null) {
                boost += (p.amount * FrostbitePower.STEP);
            }
        }
        AbstractPower p = Wiz.adp().getPower(FrostbitePower.POWER_ID);
        if (p != null) {
            boost += (p.amount * FrostbitePower.STEP);
        }
        return boost;
    }

    @Override
    public void atEndOfRound() {
        Wiz.atb(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + getDamageBoost() + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + getDamageBoost() + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3];
        }
    }
}
