package Starlight.patches;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.ui.FilterUI;
import Starlight.util.CustomTags;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.EverythingFix;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.screens.compendium.CardLibSortHeader;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;

public class CompendiumPatches {
    public static FilterType filterType = FilterType.GENERIC;
    public static boolean needsRefresh;

    @SpirePatch2(clz = EverythingFix.DidChangeTab.class, method = "Insert")
    public static class FilterCards {
        public static CardGroup filteredGroup;
        @SpirePostfixPatch
        public static void plz(CardGroup[] visibleCards) {
            if (ColorTabBarFix.Fields.getModTab().color == StarlightSisters.Enums.METEORITE_PURPLE_COLOR) {
                //We need a new group of it won't sort properly
                filteredGroup = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
                for (AbstractCard c : visibleCards[0].group) {
                    if (!filterCard(c)) {
                        filteredGroup.group.add(c);
                    }
                }
                visibleCards[0] = filteredGroup;
                needsRefresh = true;

            }
        }
    }

    @SpirePatch2(clz = CardLibraryScreen.class, method = "didChangeTab")
    public static class StopJitteringPlz {
        @SpirePostfixPatch
        public static void plz(CardLibraryScreen __instance) {
            if (needsRefresh) {
                needsRefresh = false;
                CardLibSortHeader clsb = ReflectionHacks.getPrivate(__instance, CardLibraryScreen.class, "sortHeader");
                clsb.justSorted = true;
                ReflectionHacks.privateMethod(CardLibraryScreen.class, "updateCards").invoke(__instance);
            }
        }
    }

    public static boolean filterCard(AbstractCard card) {
        switch (filterType) {
            case GENERIC:
                return !noMagicTags(card);
            case WATER:
                return !card.tags.contains(CustomTags.STARLIGHT_WATER);
            case ICE:
                return !card.tags.contains(CustomTags.STARLIGHT_ICE);
            case FIRE:
                return !card.tags.contains(CustomTags.STARLIGHT_FIRE);
            case NATURE:
                return !card.tags.contains(CustomTags.STARLIGHT_NATURE);
        }
        return false;
    }

    public static boolean noMagicTags(AbstractCard card) {
        return !card.tags.contains(CustomTags.STARLIGHT_WATER) && !card.tags.contains(CustomTags.STARLIGHT_ICE) && !card.tags.contains(CustomTags.STARLIGHT_FIRE) && !card.tags.contains(CustomTags.STARLIGHT_NATURE) && !card.tags.contains(CustomTags.STARLIGHT_DARK) && !card.tags.contains(CustomTags.STARLIGHT_EARTH) && !card.tags.contains(CustomTags.STARLIGHT_LIGHT) && !card.tags.contains(CustomTags.STARLIGHT_SAND) && !card.tags.contains(CustomTags.STARLIGHT_SPACE) && !card.tags.contains(CustomTags.STARLIGHT_STORM) && !card.tags.contains(CustomTags.STARLIGHT_TIME) && !card.tags.contains(CustomTags.STARLIGHT_VOID);
    }

    @SpirePatch2(clz = CardLibraryScreen.class, method = "render")
    public static class RenderFilters {
        @SpirePostfixPatch
        public static void plz(SpriteBatch sb, ColorTabBar ___colorBar) {
            if (ColorTabBarFix.Fields.getModTab().color == StarlightSisters.Enums.METEORITE_PURPLE_COLOR && ___colorBar.curTab == ColorTabBarFix.Enums.MOD) {
                TheStarlightMod.filterUI.render(sb);
            }
        }
    }

    @SpirePatch2(clz = CardLibraryScreen.class, method = "update")
    public static class UpdateFilters {
        @SpirePostfixPatch
        public static void plz(ColorTabBar ___colorBar) {
            if (ColorTabBarFix.Fields.getModTab().color == StarlightSisters.Enums.METEORITE_PURPLE_COLOR && ___colorBar.curTab == ColorTabBarFix.Enums.MOD) {
                TheStarlightMod.filterUI.update();
            }
        }
    }

    @SpirePatch2(clz = CardLibraryScreen.class, method = "initialize")
    public static class Plz {
        @SpirePostfixPatch
        public static void plz() {
            if (TheStarlightMod.filterUI == null) {
                TheStarlightMod.filterUI = new FilterUI();
            }
        }
    }

    public enum FilterType {
        NONE,
        GENERIC,
        WATER,
        ICE,
        FIRE,
        NATURE
    }
}
