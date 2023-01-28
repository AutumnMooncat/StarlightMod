package Starlight.patches;

import Starlight.relics.interfaces.PostEndTurnPowerRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PostEndTurnPowerRelicPatches {
    @SpirePatch2(clz = AbstractCreature.class, method = "applyEndOfTurnTriggers")
    public static class Hook {
        @SpirePostfixPatch
        public static void call(AbstractCreature __instance) {
            if (__instance instanceof AbstractPlayer) {
                for (AbstractRelic r : ((AbstractPlayer) __instance).relics) {
                    if (r instanceof PostEndTurnPowerRelic) {
                        ((PostEndTurnPowerRelic) r).postEndTurn();
                    }
                }
            }
        }
    }
}
