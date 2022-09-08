package Starlight.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import javassist.CtBehavior;

public class JustForConfirmingPatches {

    @SpirePatch(clz = GridCardSelectScreen.class, method = SpirePatch.CLASS)
    public static class DontGiveCardsField {
        public static SpireField<Boolean> dontGiveCards = new SpireField<>(() -> false);
    }

    @SpirePatch2(clz = GridCardSelectScreen.class, method = "update")
    public static class DontGiveCards {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> plz(GridCardSelectScreen __instance) {
            if (DontGiveCardsField.dontGiveCards.get(__instance)) {
                DontGiveCardsField.dontGiveCards.set(__instance, false);
                AbstractDungeon.closeCurrentScreen();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                return LineFinder.findInOrder(ctBehavior, new Matcher.FieldAccessMatcher(CardGroup.class, "group"));
            }
        }
    }
}
