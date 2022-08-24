package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.FortunaPower;
import Starlight.powers.IncantationPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.Establishment;
import com.megacrit.cardcrawl.cards.purple.Nirvana;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Fortuna extends AbstractEasyCard {
    public final static String ID = makeID(Fortuna.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Fortuna() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new FortunaPower(p, magicNumber));
    }

    public void upp() {
        this.isInnate = true;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Establishment.ID;
    }
}