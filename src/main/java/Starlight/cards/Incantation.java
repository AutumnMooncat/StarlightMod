package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.IncantationPower;
import Starlight.powers.TeamEffortPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.Nirvana;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Incantation extends AbstractEasyCard {
    public final static String ID = makeID(Incantation.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 0;
    private static final int EFFECT = 4;
    private static final int UP_EFFECT = 2;

    public Incantation() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new IncantationPower(p, magicNumber));
    }

    public void upp() {
        this.isInnate = true;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Nirvana.ID;
    }
}