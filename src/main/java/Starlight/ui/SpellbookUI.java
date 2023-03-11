package Starlight.ui;

import Starlight.TheStarlightMod;
import Starlight.patches.CompendiumPatches;
import Starlight.ui.spellbooks.*;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SpellbookUI {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("SpellbookUI")).TEXT;
    private static final float X_OFFSET = 200.0F * Settings.scale;
    private static final float HEADER_Y = Settings.HEIGHT/2f + 150f * Settings.scale;
    private static final float TEXT_Y = HEADER_Y - 75f * Settings.scale;
    private final SpellbookPanel primPanel;
    private final SpellbookPanel lunaPanel;
    private final RandomButton randomButton;

    public SpellbookUI() {
        primPanel = new SpellbookPanel(Settings.WIDTH*2/3F, Settings.HEIGHT/2F, true);
        lunaPanel = new SpellbookPanel(Settings.WIDTH/3F, Settings.HEIGHT/2F, false);
        randomButton = new RandomButton(Settings.WIDTH/2f + FontHelper.getWidth(FontHelper.bannerNameFont, TEXT[0], 1)/2F + 60f * Settings.scale, HEADER_Y);
        primPanel.addBook(new BookOfWater(true));
        primPanel.addBook(new BookOfIce(true));
        primPanel.addBook(new BookOfFire(true));
        primPanel.addBook(new BookOfNature(true));
        primPanel.addBook(new BookOfDark(true));
        primPanel.addBook(new BookOfLight(true));
        primPanel.addBook(new BookOfSpace(true));
        primPanel.addBook(new BookOfTime(true));


        lunaPanel.addBook(new BookOfWater(false));
        lunaPanel.addBook(new BookOfIce(false));
        lunaPanel.addBook(new BookOfFire(false));
        lunaPanel.addBook(new BookOfNature(false));
        lunaPanel.addBook(new BookOfDark(false));
        lunaPanel.addBook(new BookOfLight(false));
        lunaPanel.addBook(new BookOfSpace(false));
        lunaPanel.addBook(new BookOfTime(false));

        primPanel.layoutBooks();
        lunaPanel.layoutBooks();

        if (TheStarlightMod.primroseBookIndex >= primPanel.books.size() || TheStarlightMod.primroseBookIndex < 0 || TheStarlightMod.primroseBookIndex == TheStarlightMod.lunaBookIndex || TheStarlightMod.lunaBookIndex >= lunaPanel.books.size() || TheStarlightMod.lunaBookIndex < 0) {
            TheStarlightMod.primroseBookIndex = 1;
            TheStarlightMod.lunaBookIndex = 0;
            try {
                TheStarlightMod.starlightConfig.setInt(TheStarlightMod.PRIMROSE_BOOK_INDEX, TheStarlightMod.primroseBookIndex);
                TheStarlightMod.starlightConfig.setInt(TheStarlightMod.LUNA_BOOK_INDEX, TheStarlightMod.lunaBookIndex);
                TheStarlightMod.starlightConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        primPanel.books.get(TheStarlightMod.primroseBookIndex).selected = true;
        lunaPanel.books.get(TheStarlightMod.lunaBookIndex).selected = true;
    }

    public void pickRandomBooks() {
        int prim = MathUtils.random(primPanel.books.size()-1);
        int luna = (prim + 1 + MathUtils.random(lunaPanel.books.size()-2)) % lunaPanel.books.size();
        primPanel.selectBook(primPanel.books.get(prim));
        lunaPanel.selectBook(lunaPanel.books.get(luna));

    }

    public void onSwapBook(int newIndex, int oldIndex, SpellbookPanel panel) {
        if (panel == primPanel && lunaPanel.books.indexOf(lunaPanel.getSelectedBook()) == newIndex) {
            lunaPanel.selectBook(lunaPanel.books.get(oldIndex));
        } else if (panel == lunaPanel && primPanel.books.indexOf(primPanel.getSelectedBook()) == newIndex) {
            primPanel.selectBook(primPanel.books.get(oldIndex));
        }
    }

    public void update() {
        randomButton.update();
        primPanel.update();
        lunaPanel.update();
    }

    public void render(SpriteBatch sb, float xInfo) {
        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, TEXT[0], Settings.WIDTH/2f + xInfo - X_OFFSET, HEADER_Y, Settings.GOLD_COLOR);
        FontHelper.renderFontCentered(sb, FontHelper.tipHeaderFont, TEXT[1], Settings.WIDTH/2f+ xInfo - X_OFFSET, TEXT_Y, Color.WHITE);
        randomButton.render(sb);
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

    public String getPrimroseStarter() {
        return primPanel.getStarterCard();
    }

    public String getLunaStarter() {
        return lunaPanel.getStarterCard();
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

    public boolean isPrimCard(AbstractCard c) {
        return primPanel.getSelectedBook().allowCardInPool(c);
    }

    public boolean isLunaCard(AbstractCard c) {
        return lunaPanel.getSelectedBook().allowCardInPool(c);
    }

    public void setupAbilities() {
        Wiz.applyToSelf(primPanel.getSelectedBook().getAbility(true));
        Wiz.applyToSelf(lunaPanel.getSelectedBook().getAbility(false));
    }
}
