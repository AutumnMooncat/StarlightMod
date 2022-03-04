package Starlight.patches;

import Starlight.characters.Starfarers;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import javassist.CtBehavior;

public class DoubleTurnPatches {
    @SpirePatch2(clz = GameActionManager.class, method = "getNextAction")
    public static class TakeTwoTurnsEveryTurn {
        @SpireInsertPatch(locator = Locator.class)
        public static void balanceAchieved() {
            if (!AbstractDungeon.getCurrRoom().skipMonsterTurn && AbstractDungeon.player instanceof Starfarers && GameActionManager.turn != 0) {
                ((Starfarers) AbstractDungeon.player).hotswap();
            }
        }
    }
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(MonsterGroup.class, "areMonstersBasicallyDead");
            int[] ret = LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            ret[0]++;
            return ret;
        }
    }
}
