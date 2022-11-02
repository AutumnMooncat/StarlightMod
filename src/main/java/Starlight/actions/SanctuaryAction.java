package Starlight.actions;

import Starlight.powers.BarbPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class SanctuaryAction extends AbstractGameAction {

    public SanctuaryAction(AbstractCreature source, int multi) {
        this.source = source;
        this.amount = multi;
    }

    @Override
    public void update() {
        if (source.hasPower(BarbPower.POWER_ID)) {
            this.addToTop(new GainBlockAction(this.source, this.source, this.amount * source.getPower(BarbPower.POWER_ID).amount));
        }
        this.isDone = true;
    }
}
