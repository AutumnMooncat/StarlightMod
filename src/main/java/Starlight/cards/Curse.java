package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.JinxPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.green.Malaise;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Curse extends AbstractEasyCard {
    public final static String ID = makeID(Curse.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 5;
    private static final int UP_EFFECT = 2;
    private static final int SP = 2;

    public Curse() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        //baseSecondMagic = secondMagic = SP;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //Wiz.applyToEnemy(m, new JinxPower(m, magicNumber));
        Wiz.atb(new ApplyPowerAction(m, p, new JinxPower(m, magicNumber), magicNumber, AbstractGameAction.AttackEffect.POISON));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Malaise.ID;
    }
}