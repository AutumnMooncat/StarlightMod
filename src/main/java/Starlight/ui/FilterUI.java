package Starlight.ui;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.patches.CompendiumPatches;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.EverythingFix;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;
import com.megacrit.cardcrawl.screens.mainMenu.TabBarListener;

import java.util.function.Predicate;

public class FilterUI {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("FilterUI")).TEXT;
    private static final float X_OFFSET = 70.0F * Settings.scale;
    private static final float HEADER_Y = Settings.HEIGHT/2f + 200f * Settings.scale;
    private static final float TEXT_Y = HEADER_Y - 75f * Settings.scale;
    private final FilterPanel filterPanel;

    public FilterUI() {
        CardGroup group = EverythingFix.Fields.cardGroupMap.get(StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        int size = group.size();
        //filterPanel = new FilterPanel(Settings.WIDTH/2f, Settings.HEIGHT - 100f * Settings.scale);
        filterPanel = new FilterPanel(Settings.WIDTH - X_OFFSET, Settings.HEIGHT/2f);
        filterPanel.addFilter(new ClickableFilter(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID+"Resources/images/books/BookEmpty.png"), TEXT[0], makeBody(size, size)) {
            @Override
            public void onSelect() {
                CompendiumPatches.filterType = CompendiumPatches.FilterType.NONE;
                refreshFilters();
            }
        });
        filterPanel.addFilter(new ClickableFilter(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID+"Resources/images/books/BookNo.png"), TEXT[1], makeBody(countByPred(group, CompendiumPatches::noMagicTags), size)) {
            @Override
            public void onSelect() {
                CompendiumPatches.filterType = CompendiumPatches.FilterType.GENERIC;
                refreshFilters();
            }
        });
        filterPanel.addFilter(new ClickableFilter(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID+"Resources/images/books/BookWater.png"), TEXT[2], makeBody(countByPred(group, c -> c.tags.contains(CustomTags.STARLIGHT_WATER)), size)) {
            @Override
            public void onSelect() {
                CompendiumPatches.filterType = CompendiumPatches.FilterType.WATER;
                refreshFilters();
            }
        });
        filterPanel.addFilter(new ClickableFilter(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID+"Resources/images/books/BookIce.png"), TEXT[3], makeBody(countByPred(group, c -> c.tags.contains(CustomTags.STARLIGHT_ICE)), size)) {
            @Override
            public void onSelect() {
                CompendiumPatches.filterType = CompendiumPatches.FilterType.ICE;
                refreshFilters();
            }
        });
        filterPanel.addFilter(new ClickableFilter(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID+"Resources/images/books/BookFire.png"), TEXT[4], makeBody(countByPred(group, c -> c.tags.contains(CustomTags.STARLIGHT_FIRE)), size)) {
            @Override
            public void onSelect() {
                CompendiumPatches.filterType = CompendiumPatches.FilterType.FIRE;
                refreshFilters();
            }
        });
        filterPanel.addFilter(new ClickableFilter(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID+"Resources/images/books/BookNature.png"), TEXT[5], makeBody(countByPred(group, c -> c.tags.contains(CustomTags.STARLIGHT_NATURE)), size)) {
            @Override
            public void onSelect() {
                CompendiumPatches.filterType = CompendiumPatches.FilterType.NATURE;
                refreshFilters();
            }
        });

        filterPanel.layoutFilters();

        filterPanel.selectFilter(filterPanel.filters.get(0));
    }

    public String makeBody(int a, int b) {
        return "("+a+"/"+b+")";
    }

    public int countByPred(CardGroup group, Predicate<AbstractCard> p) {
        return (int) group.group.stream().filter(p).count();
    }

    public void update() {
        filterPanel.update();
    }

    public void render(SpriteBatch sb) {
        filterPanel.render(sb);
    }

    public void refreshFilters() {
        if (CardCrawlGame.mainMenuScreen != null && CardCrawlGame.mainMenuScreen.cardLibraryScreen != null) {
            ColorTabBar ctb = ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.cardLibraryScreen, CardLibraryScreen.class, "colorBar");
            TabBarListener delegate = ReflectionHacks.getPrivate(ctb, ColorTabBar.class, "delegate");
            if (delegate != null) {
                delegate.didChangeTab(ctb, ColorTabBarFix.Enums.MOD);
            }
            //CardCrawlGame.mainMenuScreen.cardLibraryScreen.didChangeTab(ctb, ColorTabBarFix.Enums.MOD);
        }
    }
}
