package Starlight.cards.bookOfTime;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.PulsePower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.blue.Electrodynamics;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Pulse extends AbstractMagickCard {
    public final static String ID = makeID(Pulse.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;
    private static final int UP_COST = 2;
    private static final int SP = 1;
    private static final int UP_SP = 1;

    public Pulse() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = SP;
        tags.add(CustomTags.STARLIGHT_TIME);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new PulsePower(p, magicNumber));
    }

    public void upp() {
        //upgradeMagicNumber(UP_SP);
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return Electrodynamics.ID;
    }
}