package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.actions.ProjectSpecificCardAction;
import Starlight.cards.interfaces.OnForetoldCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ProjectNextCardCopyPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(ProjectNextCardCopyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied = true;

    public ProjectNextCardCopyPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public ProjectNextCardCopyPower(AbstractCreature owner, int amount, boolean appliedByCard) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("time");
        justApplied = appliedByCard;
        updateDescription();
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            for (int i = 0 ; i < amount; i++) {
                Wiz.att(new ProjectSpecificCardAction(card.makeStatEquivalentCopy()));
            }
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
}
