package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.cards.interfaces.InHandCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Blasphemy;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class MagickConduit extends AbstractEasyCard implements InHandCard {
    public final static String ID = makeID(MagickConduit.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -2;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 3;
    private static final int USES = 2;
    private static final int UP_USES = 1;

    public MagickConduit() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = USES;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        /*Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                int cards = (int) Wiz.adp().drawPile.group.stream().filter(c -> c instanceof AbstractMagickCard).count();
                if (cards > 0) {
                    Wiz.applyToSelfTop(new SpellPower(p, magicNumber * cards));
                    Wiz.att(new SFXAction("ORB_PLASMA_CHANNEL", 0.1f));
                }
                this.isDone = true;
            }
        });*/
    }

    @Override
    public float modifyMagicMagic(float magic, AbstractCard card) {
        return magic + magicNumber;
    }

    @Override
    public void onCardUsed(AbstractCard card) {
        if (card instanceof AbstractMagickCard && card.baseMagicNumber > 0 && secondMagic > 0) {
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
        return Blasphemy.ID;
    }
}