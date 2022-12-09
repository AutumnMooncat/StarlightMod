package Starlight.patches;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.interfaces.MagicMagicModPower;
import Starlight.util.CardInHandSuite;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MagicCardMagicPatches {
    @SpirePatch2(clz = AbstractCard.class, method = "applyPowers")
    @SpirePatch2(clz = AbstractCard.class, method = "calculateCardDamage")
    public static class MagicStuff {
        @SpirePrefixPatch
        public static void reset(AbstractCard __instance) {
            if (Wiz.isMagic(__instance)) {
                __instance.magicNumber = __instance.baseMagicNumber;
                if (__instance instanceof AbstractMagickCard) {
                    ((AbstractMagickCard) __instance).secondMagic = ((AbstractMagickCard) __instance).baseSecondMagic;
                }
            }
        }
        @SpirePostfixPatch
        public static void modifyMagic(AbstractCard __instance) {
            if (Wiz.isMagic(__instance) && __instance.baseMagicNumber > 0) {
                float mag = __instance.magicNumber;
                float mag2 = 0;
                if (__instance instanceof AbstractMagickCard) {
                    mag2 = ((AbstractMagickCard) __instance).secondMagic;
                }

                mag = CardInHandSuite.modifyMagicMagic(mag, __instance);
                mag2 = CardInHandSuite.modifyMagicMagic(mag2, __instance);

                for (AbstractPower p : Wiz.adp().powers) {
                    if (p instanceof MagicMagicModPower) {
                        mag = ((MagicMagicModPower) p).modifyMagicMagic(mag, __instance);
                        mag2 = ((MagicMagicModPower) p).modifyMagicMagic(mag2, __instance);
                    }
                }

                __instance.magicNumber = (int) mag;
                __instance.isMagicNumberModified = __instance.magicNumber != __instance.baseMagicNumber;
                if (__instance instanceof AbstractMagickCard) {
                    ((AbstractMagickCard) __instance).secondMagic = (int) mag2;
                    ((AbstractMagickCard) __instance).isSecondMagicModified = ((AbstractMagickCard) __instance).secondMagic != ((AbstractMagickCard) __instance).baseSecondMagic;
                }
            }
        }
    }
}
