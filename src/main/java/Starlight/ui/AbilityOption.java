package Starlight.ui;

import Starlight.TheStarlightMod;
import Starlight.relics.Campfire.ConstellationEffect;
import Starlight.util.AbilityManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class AbilityOption extends AbstractCampfireOption {
    private static final String ID = TheStarlightMod.makeID("AbilityOption");
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public AbilityOption() {
        this.label = TEXT[0];
        this.description = TEXT[1];
        this.img = ImageMaster.loadImage("images/ui/campfire/ritual.png");
        updateUsability();
    }

    public void updateUsability() {
        this.usable = AbilityManager.abilityPoints > 0 && !AbilityManager.getUpgradeableAbilityCards().isEmpty();
    }

    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new AbilityEffect());
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
