package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.actions.RuinLoseHPAction;
import Starlight.util.Wiz;
import Starlight.vfx.BurnToAshEffect;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RuinPower extends AbstractPower implements HealthBarRenderPower {

    public static final String POWER_ID = TheStarlightMod.makeID(RuinPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private AbstractCreature source;
    private final Color hpBarColor = Color.SLATE.cpy();

    public RuinPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.type = PowerType.DEBUFF;
        this.loadRegion("end_turn_death");
        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        flash();
        Wiz.atb(new RuinLoseHPAction(owner, source, amount, AbstractGameAction.AttackEffect.FIRE));
        if (!Wiz.adp().hasPower(DustToDustPower.POWER_ID)) {
            Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void onDeath() {
        AbstractDungeon.effectsQueue.add(new BurnToAshEffect(owner.hb.cX, owner.hb.cY));
        //Wiz.atb(new VFXAction(new BurnToAshEffect(owner.hb.cX, owner.hb.cY)));
    }

    @Override
    public int getHealthBarAmount() {
        return amount;
    }

    @Override
    public Color getColor() {
        return hpBarColor;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
