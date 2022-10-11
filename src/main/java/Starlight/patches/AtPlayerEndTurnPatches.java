package Starlight.patches;

import Starlight.powers.interfaces.MonsterOnPlayerEndTurnPower;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AtPlayerEndTurnPatches {
    @SpirePatch2(clz = AbstractCreature.class, method = "applyEndOfTurnTriggers")
    public static class Trigger {
        @SpirePostfixPatch
        public static void plz(AbstractCreature __instance) {
            if (__instance.isPlayer) {
                Wiz.forAllMonstersLiving(mon -> {
                    for (AbstractPower p : mon.powers) {
                        if (p instanceof MonsterOnPlayerEndTurnPower) {
                            ((MonsterOnPlayerEndTurnPower) p).atEndOfPlayerTurn();
                        }
                    }
                });
            }
        }
    }
}
