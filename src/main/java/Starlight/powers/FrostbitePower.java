package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FrostbitePower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(FrostbitePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final int STEP = 30;

    public FrostbitePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("int");
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        updateChill();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateChill();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        updateChill();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        updateChill();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (amount*STEP) + DESCRIPTIONS[1];
    }

    public void updateChill() {
        Wiz.att(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractPower p : Wiz.adp().powers) {
                    if (p instanceof ChillPower) {
                        p.updateDescription();
                        p.flashWithoutSound();
                    }
                }
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    for (AbstractPower p : m.powers) {
                        if (p instanceof ChillPower) {
                            p.updateDescription();
                            p.flashWithoutSound();
                        }
                    }
                }
                this.isDone = true;
            }
        });
    }
}
