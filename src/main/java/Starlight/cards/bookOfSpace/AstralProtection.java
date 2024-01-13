package Starlight.cards.bookOfSpace;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.AstralProtectionPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.blue.Buffer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class AstralProtection extends AbstractMagickCard {
    public final static String ID = makeID(AstralProtection.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 1;

    public AstralProtection() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_SPACE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new AstralProtectionPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
        //upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return Buffer.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.61f, 0.63f, 0.54f, 0.54f, false);
    }
}