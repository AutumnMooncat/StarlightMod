package Starlight.patches;

import Starlight.cards.interfaces.LunaCard;
import Starlight.cards.interfaces.OnTagTeamTriggeredCard;
import Starlight.cards.interfaces.PrimroseCard;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.characters.StarlightSisters;
import Starlight.powers.interfaces.ModTagAmountPower;
import Starlight.powers.interfaces.OnTagTeamPower;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

public class TagTeamPatch {
    @SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
    public static class TriggerTagCards {
        @SpireInsertPatch(locator = Locator.class)
        public static void plz(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster) {
            if (c instanceof TagTeamCard && checkTagCard(c)) {
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
            if (c instanceof PrimroseCard && checkPrimCard(c)) {
                ((PrimroseCard) c).onPrimTrigger(__instance, monster);
            }
            if (c instanceof LunaCard && checkLunaCard(c)) {
                ((LunaCard) c).onLunaTrigger(__instance, monster);
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

    public static boolean checkTagCard(AbstractCard c) {
        if (c instanceof TagTeamCard) {
            if (AbstractDungeon.player instanceof StarlightSisters) {
                return CardCounterPatches.swapsThisTurn > 0;
            } else {
                AbstractCard previous = Wiz.secondLastCardPlayed();
                if (previous != null) {
                    switch (previous.type) {
                        case ATTACK:
                            return c.type == AbstractCard.CardType.SKILL;
                        case SKILL:
                            return c.type == AbstractCard.CardType.ATTACK;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkPrimCard(AbstractCard c) {
        if (c instanceof PrimroseCard) {
            if (AbstractDungeon.player instanceof StarlightSisters) {
                return ((StarlightSisters) AbstractDungeon.player).attackerInFront;
            } else {
                AbstractCard previous = Wiz.lastCardPlayed();
                if (previous != null) {
                    return c.type == AbstractCard.CardType.ATTACK;
                }
            }
        }
        return false;
    }

    public static boolean checkLunaCard(AbstractCard c) {
        if (c instanceof LunaCard) {
            if (AbstractDungeon.player instanceof StarlightSisters) {
                return !((StarlightSisters) AbstractDungeon.player).attackerInFront;
            } else {
                AbstractCard previous = Wiz.lastCardPlayed();
                if (previous != null) {
                    return c.type == AbstractCard.CardType.SKILL;
                }
            }
        }
        return false;
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
