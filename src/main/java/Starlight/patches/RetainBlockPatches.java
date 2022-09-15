package Starlight.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class RetainBlockPatches {
    public static boolean retainBlockThisTurn;

    @SpirePatch2(clz = GameActionManager.class, method = "getNextAction")
    public static class RetainBlock {
        @SpireInstrumentPatch
        public static ExprEditor plz() {
            return new ExprEditor() {
                int hits = 0;
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (hits == 0 && m.getClassName().equals(AbstractPlayer.class.getName()) && m.getMethodName().equals("hasPower")) {
                        ++hits;
                        m.replace("$_ = $proceed($$) || Starlight.patches.RetainBlockPatches.checkRetain();");
                    }
                }
            };
        }
    }

    public static boolean checkRetain() {
        if (retainBlockThisTurn) {
            retainBlockThisTurn = false;
            return true;
        }
        return false;
    }
}
