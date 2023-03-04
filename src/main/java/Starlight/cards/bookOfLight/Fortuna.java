package Starlight.cards.bookOfLight;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.FortunaPower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.Establishment;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Fortuna extends AbstractMagickCard {
    public final static String ID = makeID(Fortuna.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Fortuna() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_LIGHT);
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