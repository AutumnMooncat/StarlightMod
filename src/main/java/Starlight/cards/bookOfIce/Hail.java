package Starlight.cards.bookOfIce;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.HailPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.red.FeelNoPain;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Hail extends AbstractMagickCard {
    public final static String ID = makeID(Hail.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int AMOUNT = 3;
    private static final int UP_AMOUNT = 1;

    public Hail() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = AMOUNT;
        tags.add(CustomTags.STARLIGHT_ICE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new HailPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UP_AMOUNT);
    }

    @Override
    public String cardArtCopy() {
        return FeelNoPain.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.05f, 0.55f, 0.55f, 0.45f, false);
    }
}