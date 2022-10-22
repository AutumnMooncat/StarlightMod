package Starlight.patches;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CardPoolPatches {
    @SpirePatch2(clz = AbstractDungeon.class, method = "initializeCardPools")
    public static class LoadSpellbookCards {
        @SpirePostfixPatch
        public static void plz(AbstractDungeon __instance) {
            if (AbstractDungeon.player.chosenClass == StarlightSisters.Enums.THE_SISTERS) {
                TheStarlightMod.spellUI.fillCardPools();
            }
        }
    }
}
