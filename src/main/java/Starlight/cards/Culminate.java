package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.InHandCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Aggregate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Culminate extends AbstractEasyCard implements InHandCard {
    public final static String ID = makeID(Culminate.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -2;
    private static final int EFFECT = 4;
    private static final int UP_EFFECT = 2;
    private static final int USES = 4;

    public Culminate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = USES;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //Wiz.atb(new BoostDamageInHandAction(magicNumber, c -> c.type == CardType.ATTACK));
    }

    @Override
    public float atDamageGive(float dmg, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        return dmg + magicNumber;
    }

    @Override
    public void onCardUsed(AbstractCard card) {
        if (card.baseDamage > 0 && secondMagic > 0) {
            secondMagic--;
            isSecondMagicModified = baseSecondMagic != secondMagic;
            flash();
        }
        if (secondMagic <= 0) {
            Wiz.atb(new DiscardSpecificCardAction(this, Wiz.adp().hand));
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public void resetAttributes() {
        int current = secondMagic;
        super.resetAttributes();
        if (current > 0) {
            secondMagic = current;
            isSecondMagicModified = secondMagic != baseSecondMagic;
        }
    }

    @Override
    public String cardArtCopy() {
        return Aggregate.ID;
    }
}