package Starlight.patches;

import Starlight.TheStarlightMod;
import Starlight.cards.interfaces.OnTagTeamTriggeredCard;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.powers.interfaces.ModTagAmountPower;
import Starlight.powers.interfaces.OnTagTeamPower;
import Starlight.ui.ProjectedCardManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

public class TagTeamPatch {
    @SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
    public static class triggerTagTeam {
        @SpireInsertPatch(locator = Locator.class)
        public static void plz(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster) {
            if (c instanceof TagTeamCard && TheStarlightMod.tagTest(c)) {
                int tagTriggers = 1;
                for (AbstractPower p : __instance.powers) {
                    if (p instanceof ModTagAmountPower) {
                        tagTriggers = ((ModTagAmountPower) p).tagAmount(tagTriggers);
                    }
                }
                for (int i = 0 ; i < tagTriggers ; i++) {
                    ((TagTeamCard) c).onTagTrigger(__instance, monster);
                }
                for (AbstractPower p : __instance.powers) {
                    if (p instanceof OnTagTeamPower) {
                        for (int i = 0 ; i < tagTriggers ; i++) {
                            ((OnTagTeamPower) p).onTagTeam(c);
                        }
                    }
                }
                triggerOnTagTeamCards(c, __instance, monster, tagTriggers);
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

    public static void triggerOnTagTeamCards(AbstractCard card, AbstractPlayer p, AbstractMonster m, int times) {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(p.hand.group);
        cards.addAll(p.drawPile.group);
        cards.addAll(p.discardPile.group);
        cards.addAll(p.exhaustPile.group);
        cards.addAll(p.limbo.group);
        cards.addAll(ProjectedCardManager.cards.group);

        for (AbstractCard c : cards) {
            if (c instanceof OnTagTeamTriggeredCard) {
                for (int i = 0 ; i < times ; i++) {
                    ((OnTagTeamTriggeredCard) c).onTagTriggered(card, p, m);
                }
            }
        }
    }
}
