package Starlight.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

public class CardCounterPatches {
    public static int cardsProjectedThisTurn;
    public static int cardsProjectedThisCombat;
    public static int cardsForetoldThisTurn;
    public static int cardsForetoldThisCombat;
    public static int swapsThisTurn;
    public static int swapsThisCombat;
    public static AbstractCreature lastAttacker;

    @SpirePatch2(clz = GameActionManager.class, method = "clear")
    public static class ResetCounters {
        @SpirePrefixPatch
        public static void reset() {
            cardsForetoldThisCombat = 0;
            cardsForetoldThisTurn = 0;
            cardsProjectedThisCombat = 0;
            cardsProjectedThisTurn = 0;
            swapsThisTurn = 0;
            swapsThisCombat = 0;
        }
    }

    @SpirePatch2(clz = GameActionManager.class, method = "getNextAction")
    public static class NewTurnCounters {
        @SpireInsertPatch(locator = Locator.class)
        public static void reset() {
            cardsForetoldThisTurn = 0;
            cardsProjectedThisTurn = 0;
            swapsThisTurn = 0;
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                return LineFinder.findInOrder(ctBehavior, new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyStartOfTurnRelics"));
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "damage")
    public static class GetAttacker {
        @SpirePrefixPatch
        public static void plz(DamageInfo info) {
            if (info.owner instanceof AbstractMonster) {
                lastAttacker = info.owner;
            }
        }
    }
}
