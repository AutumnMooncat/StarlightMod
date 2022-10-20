package Starlight.patches;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.ui.SpellbookUI;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

public class SelectScreenPatches {
    @SpirePatch2(clz = CharacterOption.class, method = "update")
    public static class UpdateHook {

        @SpirePostfixPatch
        public static void updateCharOption(CharacterOption __instance) {
            if (__instance.c instanceof StarlightSisters && __instance.selected) {
                TheStarlightMod.spellUI.update();
            }
        }
    }

    @SpirePatch2(clz = CharacterOption.class, method = "renderInfo")
    public static class RenderHook {
        @SpirePostfixPatch()
        public static void renderCharOption(CharacterOption __instance, SpriteBatch sb, float ___infoX) {
            if (__instance.c instanceof StarlightSisters && __instance.selected) {
                TheStarlightMod.spellUI.render(sb, ___infoX);
            }
        }
    }

    @SpirePatch2(clz = CharacterOption.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {String.class, AbstractPlayer.class, Texture.class, Texture.class})
    public static class RaiseText {
        @SpirePostfixPatch
        public static void lift(AbstractPlayer c, @ByRef float[] ___infoY) {
            if (c instanceof StarlightSisters) {
                ___infoY[0] = Settings.HEIGHT * 3/4f;
            }
        }
    }
}
