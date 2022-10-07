package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.InHandCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Stack;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Fortify extends AbstractEasyCard implements InHandCard {
    public final static String ID = makeID(Fortify.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -2;
    private static final int USES = 3;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 1;

    public Fortify() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseSecondMagic = secondMagic = USES;
        baseMagicNumber = magicNumber = EFFECT;
        selfRetain = true;
        //CardModifierManager.addModifier(this, new ResonantMod(true));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        /*for (int i = 0 ; i < magicNumber ; i++) {
            blck();
        }*/
        //Wiz.atb(new DrawCardAction(magicNumber));
    }

    @Override
    public float onModifyBlock(float blk, AbstractCard card) {
        return blk + magicNumber;
    }

    @Override
    public void onCardUsed(AbstractCard card) {
        if (card.baseBlock > 0 && secondMagic > 0) {
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
        //upgradeBlock(UP_BLK);
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

    /*@Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new DrawCardAction(magicNumber));
    }*/

    @Override
    public String cardArtCopy() {
        return Stack.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.15f, 0.5f, 0.5f, 0.5f, false);
    }
}