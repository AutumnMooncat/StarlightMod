package Starlight.cards.bookOfDark;

import Starlight.actions.LethalDamageAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.damageMods.PiercingDamage;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Skewer;
import com.megacrit.cardcrawl.cards.purple.FearNoEvil;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import java.util.HashSet;

import static Starlight.TheStarlightMod.makeID;

public class ShadowArrow extends AbstractMagickCard {
    public final static String ID = makeID(ShadowArrow.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 7;
    private static final int UP_DMG = 3;

    public ShadowArrow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        DamageModifierManager.addModifier(this, new PiercingDamage());
        tags.add(CustomTags.STARLIGHT_DARK);
        tags.add(CustomTags.STARLIGHT_ARROW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        HashSet<Class<?>> blah = new HashSet<>();
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return Skewer.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.35f, 0.55f, 0.55f, 0.45f, false);
    }
}