package Starlight.cards;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Fire extends AbstractMagickCard {
    public final static String ID = makeID(Fire.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 5;
    private static final int UP_DMG = 2;
    private static final int VIGOR = 2;
    private static final int UP_VIGOR = 1;

    public Fire() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = VIGOR;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        Wiz.atb(new ModifyDamageAction(this.uuid, magicNumber));
        //Wiz.applyToSelf(new VigorPower(p, magicNumber));
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
        upgradeMagicNumber(UP_VIGOR);
    }

    @Override
    public String cardArtCopy() {
        return Eruption.ID;
    }
}