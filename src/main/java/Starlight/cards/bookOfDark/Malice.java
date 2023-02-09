package Starlight.cards.bookOfDark;

import Starlight.actions.ExhaustByPredAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.green.Nightmare;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static Starlight.TheStarlightMod.makeID;

public class Malice extends AbstractMagickCard {
    public final static String ID = makeID(Malice.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int UP_COST = 0;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 2;

    public Malice() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_DARK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new PoisonPower(m, p, magicNumber));
        Wiz.atb(new ExhaustByPredAction(Wiz.adp().hand, 1));
    }

    public void upp() {
        //upgradeBaseCost(UP_COST);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Nightmare.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.41f, 0.63f, 0.54f, 0.54f, false);
    }
}