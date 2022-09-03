package Starlight.cards;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.green.QuickSlash;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static Starlight.TheStarlightMod.makeID;

public class Aero extends AbstractMagickCard {
    public final static String ID = makeID(Aero.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 5;
    private static final int UP_DMG = 3;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Aero() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_WHIRLWIND"));
        this.addToBot(new VFXAction(new WhirlwindEffect(), 0.3F));
        allDmg(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        Wiz.atb(new DrawCardAction(magicNumber));
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        //upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return QuickSlash.ID;
    }
}