package Starlight.actions;

import Starlight.cards.interfaces.OnSwapCard;
import Starlight.cards.interfaces.OnTagTeamTriggeredCard;
import Starlight.characters.StarlightSisters;
import Starlight.patches.CardCounterPatches;
import Starlight.powers.TagTeamPower;
import Starlight.powers.interfaces.OnSwapPower;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

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
            CardCounterPatches.swapsThisTurn++;
            CardCounterPatches.swapsThisCombat++;
            if (sisters.hasPower(TagTeamPower.POWER_ID)) {
                ((TagTeamPower)sisters.getPower(TagTeamPower.POWER_ID)).onSwap();
            }
            for (AbstractPower p : sisters.powers) {
                if (p instanceof OnSwapPower) {
                    ((OnSwapPower) p).onSwap(sisters.attackerInFront);
                }
            }
            ArrayList<AbstractCard> cards = new ArrayList<>();
            cards.addAll(sisters.hand.group);
            cards.addAll(sisters.drawPile.group);
            cards.addAll(sisters.discardPile.group);
            cards.addAll(sisters.exhaustPile.group);
            cards.addAll(sisters.limbo.group);
            cards.addAll(ProjectedCardManager.cards.group);

            for (AbstractCard c : cards) {
                if (c instanceof OnSwapCard) {
                    ((OnSwapCard) c).onSwap(sisters.attackerInFront);
                }
            }
        }
        this.isDone = true;
    }
}
