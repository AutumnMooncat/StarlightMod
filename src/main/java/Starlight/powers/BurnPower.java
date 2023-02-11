package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.actions.BurnDamageAction;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BurnPower extends AbstractPower implements HealthBarRenderPower {

    public static final String POWER_ID = TheStarlightMod.makeID(BurnPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public final AbstractCreature source;

    private final Color hpBarColor = new Color(-2686721);

    public BurnPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.type = PowerType.DEBUFF;
        this.loadRegion("flameBarrier");
        updateDescription();
    }

    //TODO damage action always modified tint and causes some Hexaghost jank, try rebuilding Poison Action to as to not flash attack and tint if HP is <= 0?
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            Wiz.atb(new BurnDamageAction(owner, source, amount, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int getHealthBarAmount() {
        if (owner.hasPower(IntangiblePower.POWER_ID) || owner.hasPower(IntangiblePlayerPower.POWER_ID)) {
            return 1;
        }
        return amount;
    }

    @Override
    public Color getColor() {
        return hpBarColor;
    }
}
