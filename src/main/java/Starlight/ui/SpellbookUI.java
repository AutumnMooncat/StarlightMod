package Starlight.ui;

import Starlight.TheStarlightMod;
import Starlight.patches.CompendiumPatches;
import Starlight.ui.spellbooks.*;
import Starlight.util.CustomTags;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.ArrayList;
import java.util.Objects;

public class SpellbookUI {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("SpellbookUI")).TEXT;
    private static final float X_OFFSET = 200.0F * Settings.scale;
    private static final float HEADER_Y = Settings.HEIGHT/2f + 100f * Settings.scale;
    private static final float TEXT_Y = HEADER_Y - 75f * Settings.scale;
    private final SpellbookPanel primPanel;
    private final SpellbookPanel lunaPanel;

    public SpellbookUI() {
        primPanel = new SpellbookPanel(Settings.WIDTH*2/3F, Settings.HEIGHT/2F, true);
        lunaPanel = new SpellbookPanel(Settings.WIDTH/3F, Settings.HEIGHT/2F, false);
        primPanel.addBook(new BookOfIce());
        primPanel.addBook(new BookOfWater());
        primPanel.addBook(new BookOfFire());
        primPanel.addBook(new BookOfNature());


        lunaPanel.addBook(new BookOfIce());
        lunaPanel.addBook(new BookOfWater());
        lunaPanel.addBook(new BookOfFire());
        lunaPanel.addBook(new BookOfNature());

        primPanel.layoutBooks();
        lunaPanel.layoutBooks();

        primPanel.books.get(TheStarlightMod.primroseBookIndex).selected = true;
        lunaPanel.books.get(TheStarlightMod.lunaBookIndex).selected = true;
    }

    public void onSwapBook(int newIndex, int oldIndex, SpellbookPanel panel) {
        if (panel == primPanel && lunaPanel.books.indexOf(lunaPanel.getSelectedBook()) == newIndex) {
            lunaPanel.selectBook(lunaPanel.books.get(oldIndex));
        } else if (panel == lunaPanel && primPanel.books.indexOf(primPanel.getSelectedBook()) == newIndex) {
            primPanel.selectBook(primPanel.books.get(oldIndex));
        }
    }

    public void update() {
        primPanel.update();
        lunaPanel.update();
    }

    public void render(SpriteBatch sb, float xInfo) {
        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, TEXT[0], Settings.WIDTH/2f + xInfo - X_OFFSET, HEADER_Y, Settings.GOLD_COLOR);
        FontHelper.renderFontCentered(sb, FontHelper.tipHeaderFont, TEXT[1], Settings.WIDTH/2f+ xInfo - X_OFFSET, TEXT_Y, Color.WHITE);
        primPanel.render(sb);
        lunaPanel.render(sb);
    }

    public ArrayList<String> getStarterCards() {
        ArrayList<String> ret = new ArrayList<>();
        String id = primPanel.getStarterCard();
        if (!Objects.equals(id, "")) {
            ret.add(id);
        }
        id = lunaPanel.getStarterCard();
        if (!Objects.equals(id, "")) {
            ret.add(id);
        }
        return ret;
    }

    public void fillCardPools() {
        ArrayList<CardGroup> groups = new ArrayList<>();
        ClickableSpellbook primBook, lunaBook;
        primBook = primPanel.getSelectedBook();
        lunaBook = lunaPanel.getSelectedBook();
        groups.add(AbstractDungeon.commonCardPool);
        groups.add(AbstractDungeon.srcCommonCardPool);
        groups.add(AbstractDungeon.uncommonCardPool);
        groups.add(AbstractDungeon.srcUncommonCardPool);
        groups.add(AbstractDungeon.rareCardPool);
        groups.add(AbstractDungeon.srcRareCardPool);
        for (CardGroup g : groups) {
            g.group.removeIf(c -> !(CompendiumPatches.noMagicTags(c) || primBook.allowCardInPool(c) || lunaBook.allowCardInPool(c)));
        }
    }
}
