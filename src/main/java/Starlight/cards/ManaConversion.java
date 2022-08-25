package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.FortunaPower;
import Starlight.powers.ManaConversionPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.blue.StaticDischarge;
import com.megacrit.cardcrawl.cards.purple.Establishment;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class ManaConversion extends AbstractEasyCard {
    public final static String ID = makeID(ManaConversion.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 1;

    public ManaConversion() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ManaConversionPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return StaticDischarge.ID;
    }
}