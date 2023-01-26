package Starlight.cards.bookOfNature;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.colorless.Transmutation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class SolarCharge extends AbstractMagickCard {
    public final static String ID = makeID(SolarCharge.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int DMG = 9;
    private static final int UP_DMG = 3;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public SolarCharge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        //baseDamage = damage = DMG;
        //baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_NATURE);
        cardsToPreview = new SolarBlade();
        //tags.add(CustomTags.STARLIGHT_APPLIES_BURN);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.topDeck(cardsToPreview.makeStatEquivalentCopy());
    }

    public void upp() {
        //upgradeDamage(UP_DMG);
        //upgradeMagicNumber(UP_EFFECT);
        cardsToPreview.upgrade();
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Transmutation.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.65f, 0.65f, 0.45f, false);
    }

}