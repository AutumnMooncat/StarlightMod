package Starlight.patches;

import Starlight.powers.interfaces.OnManualDiscardPower;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class OnManualDiscardPatches {
    @SpirePatch2(clz = GameActionManager.class, method = "incrementDiscard")
    public static class DiscardPatch {
        @SpirePostfixPatch
        public static void onDiscard(boolean endOfTurn) {
            if (!AbstractDungeon.actionManager.turnHasEnded && !endOfTurn) {
                for (AbstractPower p : Wiz.adp().powers) {
                    if (p instanceof OnManualDiscardPower) {
                        ((OnManualDiscardPower) p).onManualDiscard();
                    }
                }
            }
        }
    }
}
