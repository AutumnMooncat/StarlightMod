package Starlight.actions;

import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ImmediateRemoveAllBlockAction extends RemoveAllBlockAction {
    public ImmediateRemoveAllBlockAction(AbstractCreature target, AbstractCreature source) {
        super(target, source);
    }

    @Override
    public void update() {
        super.update();
        this.isDone = true;
    }
}
