package Starlight.cards.bookOfFire;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.FirestormPower;
import Starlight.powers.VulcanizePower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Firestorm extends AbstractMagickCard {
    public final static String ID = makeID(Firestorm.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Firestorm() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_FIRE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new FirestormPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }
}