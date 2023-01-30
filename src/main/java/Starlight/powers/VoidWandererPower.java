package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.powers.interfaces.OnProjectPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class VoidWandererPower extends AbstractPower implements OnProjectPower {

    public static final String POWER_ID = TheStarlightMod.makeID(VoidWandererPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public VoidWandererPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("fading");
        updateDescription();
    }

    /*@Override
    public void onExhaust(AbstractCard card) {
        flash();
        Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new JinxPower(mon, amount)));
    }*/

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onProject(AbstractCard card, boolean isEndTurn) {
        if (!isEndTurn) {
            flash();
            Wiz.att(new DrawCardAction(amount));
        }
    }
}
