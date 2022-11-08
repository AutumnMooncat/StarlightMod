package Starlight.patches;

import Starlight.ui.ProjectedCardManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.HashSet;
import java.util.UUID;

public class FindProjectedCardsPatches {
    @SpirePatch2(clz = GetAllInBattleInstances.class, method = "get")
    public static class AddProjectedCards {
        @SpirePostfixPatch()
        public static HashSet<AbstractCard> plz(HashSet<AbstractCard> __result, UUID uuid) {
            for (AbstractCard card : ProjectedCardManager.cards.group) {
                if (card.uuid == uuid) {
                    __result.add(card);
                }
            }
            return __result;
        }
    }
}
