package Starlight.actions;

import Starlight.characters.StarlightSisters;
import Starlight.powers.TagTeamPower;
import Starlight.powers.interfaces.OnSwapPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SwapAction extends AbstractGameAction {

    public SwapAction() {
        actionType = ActionType.DIALOG;
    }

    @Override
    public void update() {
        if (Wiz.adp() instanceof StarlightSisters) {
            StarlightSisters sisters = (StarlightSisters) Wiz.adp();
            if (sisters.attackerInFront) {
                sisters.playAnimation("SkillSwap");
                sisters.attackerInFront = false;
            } else {
                sisters.playAnimation("AttackSwap");
                sisters.attackerInFront = true;
            }
            if (sisters.hasPower(TagTeamPower.POWER_ID)) {
                ((TagTeamPower)sisters.getPower(TagTeamPower.POWER_ID)).onSwap();
            }
            for (AbstractPower p : sisters.powers) {
                if (p instanceof OnSwapPower) {
                    ((OnSwapPower) p).onSwap(sisters.attackerInFront);
                }
            }
        }
        this.isDone = true;
    }
}
