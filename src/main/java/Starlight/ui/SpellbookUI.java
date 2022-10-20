package Starlight.ui;

import Starlight.TheStarlightMod;
import Starlight.ui.spellbooks.BookOfFire;
import Starlight.ui.spellbooks.BookOfIce;
import Starlight.ui.spellbooks.BookOfNature;
import Starlight.ui.spellbooks.BookOfWater;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.ArrayList;
import java.util.Objects;

public class SpellbookUI {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("SpellbookUI")).TEXT;
    private static final float X_OFFSET = 200.0F * Settings.scale;
    private static final float HEADER_Y = Settings.HEIGHT/2f + 200f * Settings.scale;
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
}
