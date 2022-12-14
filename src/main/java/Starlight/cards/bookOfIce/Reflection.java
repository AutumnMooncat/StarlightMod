package Starlight.cards.bookOfIce;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.ReflectPower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.blue.AutoShields;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Reflection extends AbstractMagickCard {
    public final static String ID = makeID(Reflection.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLK = 5;
    private static final int UP_BLK = 2;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 2;

    public Reflection() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_ICE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new ReflectPower(p, magicNumber));
    }

    public void upp() {
        upgradeBlock(UP_BLK);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return AutoShields.ID;
    }
}