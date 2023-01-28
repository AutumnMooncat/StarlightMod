package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.util.Wiz;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StricturePower extends AbstractPower implements HealthBarRenderPower {

    public static final String POWER_ID = TheStarlightMod.makeID(StricturePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AbstractCreature source;

    private final Color hpBarColor = new Color(0xc7c3b5ff);

    public StricturePower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.loadRegion("shackle");
        updateDescription();
        this.source = source;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.owner == owner && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            doEffect();
        }
    }

    @Override
    public void onSpecificTrigger() {
        doEffect();
    }

    public void doEffect() {
        //Wiz.att(new LoseHPAction(owner, source, amount, AbstractGameAction.AttackEffect.FIRE));
        Wiz.att(new DamageAction(owner, new DamageInfo(source, amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE, true));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int getHealthBarAmount() {
        if (owner instanceof AbstractMonster) {
            if (((AbstractMonster) owner).getIntentBaseDmg() >= 0) {
                if (ReflectionHacks.<Boolean>getPrivate(owner, AbstractMonster.class, "isMultiDmg")) {
                    return amount * ReflectionHacks.<Integer>getPrivate(owner, AbstractMonster.class, "intentMultiAmt");
                }
                return amount;
            }
            return 0;
        }
        return amount;
    }

    @Override
    public Color getColor() {
        return hpBarColor;
    }
}
