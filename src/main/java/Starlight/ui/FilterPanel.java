package Starlight.ui;

import Starlight.ui.spellbooks.ClickableSpellbook;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class FilterPanel {
    public ArrayList<ClickableFilter> filters = new ArrayList<>();
    public float x;
    public float y;
    public static final float PAD_X = (ClickableFilter.SIZE + 10f) * Settings.scale;
    public static final float PAD_Y = (ClickableFilter.SIZE + 10f) * Settings.scale;
    public static final float CORR_X = ClickableFilter.SIZE/2f * Settings.scale;
    public static final float CORR_Y = ClickableFilter.SIZE/2f * Settings.scale;
    public static final float DELTA_Y = 305f * Settings.scale;

    public FilterPanel(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void addFilter(ClickableFilter filter) {
        filters.add(filter);
    }

    public void layoutFilters() {
        int index = 0;
        for (ClickableFilter filter : filters) {
            filter.setPanel(this);
            //filter.move(x - (filters.size()-1)/2f * PAD_X + index * PAD_X - CORR_X, y);
            filter.move(x - CORR_X, y + (filters.size()-1)/2f * PAD_Y - index * PAD_Y - CORR_Y);
            index++;
        }
    }

    public void selectFilter(ClickableFilter selected) {
        for (ClickableFilter filter : filters) {
            if (filter != selected) {
                filter.selected = false;
            } else {
                filter.selected = true;
                filter.onSelect();
            }
        }
    }

    public void update() {
        for (ClickableFilter filter : filters) {
            //filter.move(filter.getX(), DELTA_Y + topCardY);
            filter.update();
        }
    }

    public void render(SpriteBatch sb) {
        for (ClickableFilter filter : filters) {
            filter.render(sb);
        }
    }
}
