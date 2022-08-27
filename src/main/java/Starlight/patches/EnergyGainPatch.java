package Starlight.patches;

import Starlight.powers.interfaces.OnGainEnergyPower;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EnergyGainPatch {
    private static boolean hookActive = true;
    @SpirePatch2(clz = EnergyPanel.class, method = "addEnergy")
    public static class TriggerOnEnergyGain {
        @SpirePrefixPatch
        public static void energyGain(int e) {
            if (hookActive) {
                for (AbstractPower pow : Wiz.adp().powers) {
                    if (pow instanceof OnGainEnergyPower) {
                        ((OnGainEnergyPower) pow).onGainEnergy(e);
                    }
                }
            }
        }
    }

    @SpirePatch2(clz = EnergyManager.class, method = "recharge")
    public static class DontDoubleUp {
        @SpirePrefixPatch
        public static void stop() {
            hookActive = false;
        }

        @SpirePostfixPatch
        public static void resume() {
            hookActive = true;
        }
    }
}
