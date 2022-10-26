package Starlight.cards.abstracts;

import Starlight.TheStarlightMod;
import Starlight.patches.TypeOverridePatch;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAbilityCard extends AbstractPowerCard {
    private static final String ID = TheStarlightMod.makeID("Abilities");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] ABILITY_TEXT = uiStrings.TEXT;
    private static List<TooltipInfo> tips;

    public AbstractAbilityCard(AbstractPower power) {
        super(power);
        TypeOverridePatch.TypeOverrideField.typeOverride.set(this, ABILITY_TEXT[0]);
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        if (tips == null) {
            tips = new ArrayList<>();
            tips.add(new TooltipInfo(ABILITY_TEXT[0], ABILITY_TEXT[1]));
        }
        return tips;
    }

}
