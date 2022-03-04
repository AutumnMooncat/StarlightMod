package Starlight.relics.Campfire;

import Starlight.TheStarsAboveMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class ConstellationOption extends AbstractCampfireOption {
    private static final String ID = TheStarsAboveMod.makeID("ConstellationOption");
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public ConstellationOption(boolean active) {
        this.label = TEXT[0];
        this.usable = active;
        this.description = active ? TEXT[1] : TEXT[2];
        this.img = ImageMaster.loadImage("images/ui/campfire/meditate.png");
    }

    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new ConstellationEffect());
        }

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
