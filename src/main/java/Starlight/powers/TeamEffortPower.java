package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TeamEffortPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(TeamEffortPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TeamEffortPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("modeShift");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        int attacking = 0;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && m.getIntentBaseDmg() >= 0 && !m.hasPower(StunMonsterPower.POWER_ID)) {
                attacking++;
            }
        }
        if (attacking > 0) {
            flash();
            Wiz.atb(new GainBlockAction(owner, owner, attacking * amount));
        }
    }

    /*@Override
    public void onTagTeam(AbstractCard card) {
        flash();
        Wiz.atb(new GainBlockAction(owner, owner, amount));
    }*/

    /*@Override
    public void onSwap(boolean toPrim) {
        flash();
        Wiz.atb(new GainBlockAction(owner, owner, amount));
    }*/
}
