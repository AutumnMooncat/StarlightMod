package Starlight.patches;

import Starlight.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.Collections;

public class ShuffleSplicePatch {
    @SpirePatch(clz = EmptyDeckShuffleAction.class, method = SpirePatch.CLASS)
    public static class SpliceField {
        public static SpireField<Boolean> splice = new SpireField<>(() -> false);
    }

    @SpirePatch2(clz = EmptyDeckShuffleAction.class, method = "update")
    public static class Splice {
        static ArrayList<AbstractCard> removedCards = new ArrayList<>();
        @SpireInsertPatch(locator = Locator.class)
        public static void removeDrawPile(EmptyDeckShuffleAction __instance) {
            if (SpliceField.splice.get(__instance)) {
                removedCards.addAll(Wiz.adp().drawPile.group);
                Wiz.adp().drawPile.clear();
            }
        }

        @SpireInsertPatch(locator = Locator2.class)
        public static void addDrawPile(EmptyDeckShuffleAction __instance) {
            if (SpliceField.splice.get(__instance)) {
                Collections.reverse(removedCards);
                for (AbstractCard c : removedCards) {
                    Wiz.adp().drawPile.addToTop(c);
                }
                removedCards.clear();
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                return LineFinder.findInOrder(ctBehavior, new Matcher.FieldAccessMatcher(CardGroup.class, "group"));
            }
        }

        public static class Locator2 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                return LineFinder.findInOrder(ctBehavior, new Matcher.FieldAccessMatcher(EmptyDeckShuffleAction.class, "isDone"));
            }
        }
    }
}
