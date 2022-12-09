package Starlight.cardmods;

import Starlight.TheStarlightMod;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;
import java.util.Collections;

public class ForcedMagicMod extends AbstractCardModifier {
    private static String ID = TheStarlightMod.makeID(ForcedMagicMod.class.getSimpleName());
    private static String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Magicks")).TEXT;
    private static ArrayList<TooltipInfo> tip = new ArrayList<>(Collections.singletonList(new TooltipInfo(TEXT[1], TEXT[2])));

    @Override
    public void onInitialApplication(AbstractCard card) {
        DamageModifierManager.addModifier(card, new ForcedMagicInfo());
        card.tags.add(CustomTags.STARLIGHT_FORCED_MAGIC);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !Wiz.isMagic(card) && !CardModifierManager.hasModifier(card, ID);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (!(card instanceof CustomCard)) {
            return "starlight:"+TEXT[0]+" NL "+rawDescription;
        }
        return rawDescription;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ForcedMagicMod();
    }

    public static class ForcedMagicInfo extends AbstractDamageModifier {
        @Override
        public ArrayList<TooltipInfo> getCustomTooltips() {
            return tip;
        }

        @Override
        public String getCardDescriptor() {
            return TEXT[0];
        }

        @Override
        public AbstractDamageModifier makeCopy() {
            return new ForcedMagicInfo();
        }

        @Override
        public boolean isInherent() {
            return true;
        }
    }
}
