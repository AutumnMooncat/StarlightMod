package Starlight.cardmods;

import Starlight.actions.ProjectSpecificCardAction;
import Starlight.patches.CustomTags;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class AscendedMod extends AbstractCardModifier {
    public static String ID = makeID(AscendedMod.class.getSimpleName());
    public static String[] TEXT = CardCrawlGame.languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION;

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0]+cardName+TEXT[1];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + TEXT[2];
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = (float)Settings.HEIGHT / 2.0F;
        tmp.purgeOnUse = true;
        Wiz.att(new ProjectSpecificCardAction(tmp));
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.tags.contains(CustomTags.ASCENDED);
    }

    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.ASCENDED);
        card.purgeOnUse = true;
    }

    public AbstractCardModifier makeCopy() {
        return new AscendedMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
