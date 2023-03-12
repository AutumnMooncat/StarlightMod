package Starlight.patches;

import Starlight.powers.interfaces.PreDamagePower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

public class PreDamageGivePatches {
    @SpirePatch(clz = AbstractCard.class, method = "calculateCardDamage")
    public static class PreDamageGive {
        @SpireInsertPatch(locator = DamageLocator.class, localvars = {"tmp"})
        public static void damageInsert(AbstractCard __instance, AbstractMonster m, @ByRef float[] tmp) {
            for (AbstractPower p : m.powers) {
                if (p instanceof PreDamagePower) {
                    tmp[0] = ((PreDamagePower) p).preDamageReceive(tmp[0], __instance);
                }
            }
        }

        @SpireInsertPatch(locator = MultiDamageLocator.class, localvars = {"tmp", "i", "m"})
        public static void multiDamageInsert(AbstractCard __instance, AbstractMonster mo, float[] tmp, int i, ArrayList<AbstractMonster> m) {
            for (AbstractPower p : m.get(i).powers) {
                if (p instanceof PreDamagePower) {
                    tmp[0] = ((PreDamagePower) p).preDamageReceive(tmp[0], __instance);
                }
            }
        }
    }

    private static class MultiDamageLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
            int[] tmp = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{tmp[2]};
        }
    }

    private static class DamageLocator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers");
            int[] tmp = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{tmp[0]};
        }
    }
}
