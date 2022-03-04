package Starlight.actions;

import Starlight.powers.DualCharacterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DualCharacterSwapAction extends AbstractGameAction {
    private final byte charByte;

    public DualCharacterSwapAction(AbstractCreature source, byte toCharacterByte) {
        this.source = source;
        this.charByte = toCharacterByte;
    }

    @Override
    public void update() {
        if (source.hasPower(DualCharacterPower.POWER_ID)) {
            ((DualCharacterPower)source.getPower(DualCharacterPower.POWER_ID)).changeForm(charByte);
        }
        this.isDone = true;
    }
}
