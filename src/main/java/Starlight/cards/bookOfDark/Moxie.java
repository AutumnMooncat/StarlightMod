package Starlight.cards.bookOfDark;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.PowerTripPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.red.Rupture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Moxie extends AbstractMagickCard {
    public final static String ID = makeID(Moxie.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Moxie() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_DARK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new PowerTripPower(p, magicNumber));
    }

    public void upp() {
        //upgradeBaseCost(UP_COST);
        isInnate = true;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Rupture.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.35f, 0.5f, 0.5f, 0.5f, false);
    }
}