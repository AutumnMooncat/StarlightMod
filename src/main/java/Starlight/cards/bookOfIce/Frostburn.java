package Starlight.cards.bookOfIce;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.ChillPower;
import Starlight.powers.FrostburnPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.red.FireBreathing;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Frostburn extends AbstractMagickCard {
    public final static String ID = makeID(Frostburn.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;
    private static final int CHILL = 2;

    public Frostburn() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = 0;
        tags.add(CustomTags.STARLIGHT_ICE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new FrostburnPower(p, magicNumber));
        if (secondMagic > 0) {
            Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new ChillPower(mon, secondMagic)));
        }
    }

    public void upp() {
        //upgradeBaseCost(UP_COST);
        //upgradeMagicNumber(UP_EFFECT);
        upgradeSecondMagic(CHILL);
    }

    @Override
    public String cardArtCopy() {
        return FireBreathing.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.0f, 0.5f, 0.5f, 0.5f, false);
    }
}