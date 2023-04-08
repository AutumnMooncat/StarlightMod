package Starlight.patches;

import Starlight.powers.interfaces.OnAnyDiscardPower;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

public class OnMoveToDiscardPatches {
    private static boolean dontTrigger;

    @SpirePatch2(clz = UseCardAction.class, method = "update")
    public static class DontCountPlayedCards {
        @SpireInsertPatch(locator = Locator.class)
        public static void dont() {
            dontTrigger = true;
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(CardGroup.class, "moveToDiscardPile");
                return LineFinder.findAllInOrder(ctBehavior, m);
            }
        }
    }
    @SpirePatch2(clz = CardGroup.class, method = "moveToDiscardPile")
    public static class MoveCardsPatch {
        @SpirePrefixPatch
        public static void move(CardGroup __instance, AbstractCard c) {
            if (__instance != Wiz.adp().discardPile && !dontTrigger) {
                for (AbstractPower p : Wiz.adp().powers) {
                    if (p instanceof OnAnyDiscardPower) {
                        ((OnAnyDiscardPower) p).onAnyDiscard(c);
                    }
                }
            }
            dontTrigger = false;
        }
    }
}
