package Starlight.ui;

import Starlight.TheStarlightMod;
import Starlight.ui.spellbooks.ClickableSpellbook;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class SpellbookPanel {
    public ArrayList<ClickableSpellbook> books = new ArrayList<>();
    public boolean primrose;
    public float x;
    public float y;
    public static final float PAD_X = (ClickableSpellbook.SIZE + 10f) * Settings.scale;
    public static final float PAD_Y = (ClickableSpellbook.SIZE + 10f) * Settings.scale;
    public static final float CORR_X = ClickableSpellbook.SIZE/2f * Settings.scale;
    public static final float CORR_Y = ClickableSpellbook.SIZE/2f * Settings.scale;
    public static final int BOOKS_PER_ROW = 4;

    public SpellbookPanel(float x, float y, boolean primrose) {
        this.x = x;
        this.y = y;
        this.primrose = primrose;
    }

    public void addBook(ClickableSpellbook book) {
        books.add(book);
    }

    public void layoutBooks() {
        int index = 0;
        int row = 0;
        int amtThisRow = Math.min(books.size(), BOOKS_PER_ROW);
        for (ClickableSpellbook book : books) {
            book.setPanel(this);
            book.move(x - (amtThisRow-1)/2f * PAD_X + index * PAD_X - CORR_X, y - row * PAD_Y - CORR_Y);
            index++;
            if (index == BOOKS_PER_ROW) {
                index = 0;
                row++;
                amtThisRow = Math.min(books.size() - (row * BOOKS_PER_ROW), BOOKS_PER_ROW);
            }
        }
    }

    public void selectBook(ClickableSpellbook selected) {
        int oldIndex = books.indexOf(getSelectedBook());
        for (ClickableSpellbook book : books) {
            if (book != selected) {
                book.selected = false;
            } else {
                book.selected = true;
                book.onSelect();
            }
        }
        TheStarlightMod.spellUI.onSwapBook(books.indexOf(getSelectedBook()), oldIndex, this);
    }

    public void update() {
        for (ClickableSpellbook book : books) {
            book.update();
        }
    }

    public void render(SpriteBatch sb) {
        for (ClickableSpellbook book : books) {
            book.render(sb);
        }
    }

    public String getStarterCard() {
        for (ClickableSpellbook book : books) {
            if (book.selected) {
                return book.starterCardID();
            }
        }
        return "";
    }

    public ClickableSpellbook getSelectedBook() {
        for (ClickableSpellbook book : books) {
            if (book.selected) {
                return book;
            }
        }
        return books.get(0);
    }
}
