package Starlight.actions;

import Starlight.characters.StarlightSisters;
import Starlight.powers.TagTeamPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class SwapAction extends AbstractGameAction {

    public SwapAction() {
        actionType = ActionType.DIALOG;
    }

    @Override
    public void update() {
        if (Wiz.adp() instanceof StarlightSisters) {
            if (((StarlightSisters) Wiz.adp()).attackerInFront) {
                ((StarlightSisters) Wiz.adp()).playAnimation("SkillSwap");
                ((StarlightSisters) Wiz.adp()).attackerInFront = false;
            } else {
                ((StarlightSisters) Wiz.adp()).playAnimation("AttackSwap");
                ((StarlightSisters) Wiz.adp()).attackerInFront = true;
            }
            if (Wiz.adp().hasPower(TagTeamPower.POWER_ID)) {
                ((TagTeamPower)Wiz.adp().getPower(TagTeamPower.POWER_ID)).onSwap();
            }
        }
        this.isDone = true;
    }
}
