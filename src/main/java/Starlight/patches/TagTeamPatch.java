package Starlight.patches;

import Starlight.TheStarlightMod;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.powers.interfaces.OnTagTeamPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

public class TagTeamPatch {
    @SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
    public static class triggerTagTeam {
        @SpireInsertPatch(locator = Locator.class)
        public static void plz(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster) {
            if (c instanceof TagTeamCard && TheStarlightMod.tagTest(c)) {
                ((TagTeamCard) c).onTagTrigger(__instance, monster);
                for (AbstractPower p : __instance.powers) {
                    if (p instanceof OnTagTeamPower) {
                        ((OnTagTeamPower) p).onTagTeam(c);
                    }
                }
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(GameActionManager.class, "addToBottom");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
