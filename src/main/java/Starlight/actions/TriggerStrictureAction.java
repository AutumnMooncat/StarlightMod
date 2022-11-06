package Starlight.actions;

import Starlight.powers.StricturePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class TriggerStrictureAction extends AbstractGameAction {

    public TriggerStrictureAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (target.hasPower(StricturePower.POWER_ID)) {
            target.getPower(StricturePower.POWER_ID).flash();
            for (int i = 0 ; i < amount ; i++) {
                target.getPower(StricturePower.POWER_ID).onSpecificTrigger();
            }
        }
        this.isDone = true;
    }
}
