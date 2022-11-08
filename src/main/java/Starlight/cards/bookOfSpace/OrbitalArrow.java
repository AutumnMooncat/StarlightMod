package Starlight.cards.bookOfSpace;

import Starlight.actions.ProjectSpecificCardAction;
import Starlight.cardmods.ExhaustMod;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.cards.interfaces.OnProjectCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.MeteorStrike;
import com.megacrit.cardcrawl.cards.green.Skewer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static Starlight.TheStarlightMod.makeID;

public class OrbitalArrow extends AbstractMagickCard {
    public final static String ID = makeID(OrbitalArrow.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 6;
    private static final int UP_DMG = 3;

    private boolean projected;

    public OrbitalArrow() {
        this(false);
    }

    public OrbitalArrow(boolean projected) {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        tags.add(CustomTags.STARLIGHT_SPACE);
        if (projected) {
            setProjected();
        }
    }

    private void setProjected() {
        this.projected = true;
        this.exhaust = true;
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (!projected) {
            OrbitalArrow a = (OrbitalArrow) makeStatEquivalentCopy();
            a.setProjected();
            Wiz.atb(new ProjectSpecificCardAction(a));
        }
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
        return new CardArtRoller.ReskinInfo(ID, 0.12f, 0.3f, 0.4f, 0.5f, false);
    }

    @Override
    public AbstractCard makeCopy() {
        return new OrbitalArrow(projected);
    }
}