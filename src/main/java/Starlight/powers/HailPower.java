package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.actions.DoIfPowerAppliedAction;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

public class HailPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(HailPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HailPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("skillBurn");
        updateDescription();
    }

    /*public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        addToBot(new VFXAction(new BlizzardEffect(amount * 2, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.2F));
        addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
    }*/

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == PowerType.DEBUFF && !power.ID.equals(GainStrengthPower.POWER_ID) && source == this.owner) {
            this.flash();
            Wiz.att(new DoIfPowerAppliedAction(power, new AbstractGameAction() {
                @Override
                public void update() {
                    addToBot(new VFXAction(new BlizzardEffect(amount * 2, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F)); // 0.2
                    //addToBot(new GainBlockAction(HailPower.this.owner, HailPower.this.owner, HailPower.this.amount));
                    addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(HailPower.this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
                    this.isDone = true;
                }
            }));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
