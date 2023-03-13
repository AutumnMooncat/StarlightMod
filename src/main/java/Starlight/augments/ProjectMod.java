package Starlight.augments;

import CardAugments.cardmods.AbstractAugment;
import Starlight.TheStarlightMod;
import Starlight.actions.ProjectCardsInHandAction;
import Starlight.cards.Star;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ProjectMod extends AbstractAugment {
    public static final String ID = TheStarlightMod.makeID(ProjectMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;

    private static final int PROJECT = 1;
    private boolean setBaseVar;

    @Override
    public void onInitialApplication(AbstractCard card) {
        if (card instanceof Star) {
            card.baseMagicNumber += PROJECT;
            card.magicNumber += PROJECT;
            setBaseVar = true;
        }
        if (!card.hasTag(CustomTags.STARLIGHT_PROJECTS)) {
            card.tags.add(CustomTags.STARLIGHT_PROJECTS);
        }
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage * MODERATE_DEBUFF;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return card.cost != -2 && card.baseDamage > 1 && card.type == AbstractCard.CardType.ATTACK;
    }

    @Override
    public String getPrefix() {
        return TEXT[0];
    }

    @Override
    public String getSuffix() {
        return TEXT[1];
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (setBaseVar) {
            return rawDescription;
        }
        return rawDescription + String.format(TEXT[2], PROJECT);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (!setBaseVar) {
            Wiz.atb(new ProjectCardsInHandAction(PROJECT));
        }
    }

    @Override
    public AugmentRarity getModRarity() {
        return AugmentRarity.RARE;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ProjectMod();
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
