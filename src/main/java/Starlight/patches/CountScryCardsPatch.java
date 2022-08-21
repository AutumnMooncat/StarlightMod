package Starlight.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import java.util.ArrayList;

public class CountScryCardsPatch {
    @SpirePatch2(clz = ScryAction.class, method = SpirePatch.CLASS)
    public static class ScriedCardsField {
        public static StaticSpireField<ArrayList<AbstractCard>> cards = new StaticSpireField<>(ArrayList::new);
    }

    public static ArrayList<AbstractCard> scriedCards() {
        return ScriedCardsField.cards.get();
    }

    @SpirePatch2(clz = ScryAction.class, method = "update")
    public static class SetCards {
        @SpireInsertPatch(locator = Locator1.class)
        public static void clearCards1(ScryAction __instance) {
            ScriedCardsField.cards.get().clear();
        }

        @SpireInsertPatch(locator = Locator2.class)
        public static void clearCards2(ScryAction __instance) {
            ScriedCardsField.cards.get().clear();
        }

        @SpireInsertPatch(locator = Locator3.class)
        public static void addCards(ScryAction __instance) {
            ScriedCardsField.cards.get().addAll(AbstractDungeon.gridSelectScreen.selectedCards);
        }

        public static class Locator1 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getMonsters");
                return new int[]{LineFinder.findInOrder(ctBehavior, m)[0]+1};
            }
        }

        public static class Locator2 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }

        public static class Locator3 extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ArrayList.class, "clear");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
