package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.InHandCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Aggregate;
import com.megacrit.cardcrawl.cards.purple.Collect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Quicksilver extends AbstractEasyCard implements InHandCard {
    public final static String ID = makeID(Quicksilver.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -2;
    private static final int EFFECT = 1;
    private static final int USES = 2;
    private static final int UP_USES = 1;

    public Quicksilver() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = USES;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void onCardUsed(AbstractCard card) {
        if (card.type == CardType.ATTACK && secondMagic > 0) {
            secondMagic--;
            isSecondMagicModified = baseSecondMagic != secondMagic;
            flash();
            Wiz.atb(new DrawCardAction(magicNumber));
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
        upgradeSecondMagic(UP_USES);
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
        return Collect.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.5f, 0.1f, 0.65f, 0.5f, false);
    }
}