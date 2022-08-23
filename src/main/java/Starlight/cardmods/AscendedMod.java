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

    private boolean echo = false;

    @Override
    public String modifyName(String cardName, AbstractCard card) {
        return TEXT[0]+cardName+TEXT[1];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (echo) {
            return rawDescription + TEXT[2];
        }
        return rawDescription;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (echo && !ProjectedCardManager.ProjectedActionField.projectedField.get(action)) {
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            Wiz.att(new ProjectSpecificCardAction(tmp));
        }
    }

    public boolean shouldApply(AbstractCard card) {
        return !card.tags.contains(CustomTags.ASCENDED);
    }

    public void onInitialApplication(AbstractCard card) {
        card.tags.add(CustomTags.ASCENDED);
        if (!card.upgraded && card.canUpgrade()) {
            card.upgrade();
        }
        boolean baseChange = false;
        if (card.baseDamage > 0) {
            card.baseDamage += Math.max(1, card.baseDamage/2f);
            baseChange = true;
        }
        if (card.baseBlock > 0) {
            card.baseBlock += Math.max(1, card.baseBlock/2f);
            baseChange = true;
        }
        if (card.baseMagicNumber > 0) {
            card.baseMagicNumber += Math.max(1, card.magicNumber/2f);
            baseChange = true;
        }
        if (!baseChange) {
            echo = true;
        }
    }

    public AbstractCardModifier makeCopy() {
        return new AscendedMod();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}
