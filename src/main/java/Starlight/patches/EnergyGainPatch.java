package Starlight.patches;

import Starlight.powers.interfaces.OnGainEnergyPower;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class EnergyGainPatch {
    @SpirePatch2(clz = EnergyPanel.class, method = "addEnergy")
    public static class TriggerOnEnergyGain {
        @SpirePrefixPatch
        public static void rng(int e) {
            for (AbstractPower pow : Wiz.adp().powers) {
                if (pow instanceof OnGainEnergyPower) {
                    ((OnGainEnergyPower) pow).onGainEnergy(e);
                }
            }
        }
    }
}
