package Starlight.cards.bookOfLight;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.StricturePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.colorless.Purity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static Starlight.TheStarlightMod.makeID;

public class Hallow extends AbstractMagickCard {
    public final static String ID = makeID(Hallow.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int EFFECT = 6;
    private static final int UP_EFFECT = 3;
    private static final int NEGATE = 2;
    private static final int UP_NEGATE = 1;

    public Hallow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = NEGATE;
        tags.add(CustomTags.STARLIGHT_LIGHT);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ArtifactPower(p, secondMagic));
        Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new StricturePower(mon, p, magicNumber)));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
        upgradeSecondMagic(UP_NEGATE);
    }

    @Override
    public String cardArtCopy() {
        return Purity.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.05f, 0.55f, 0.65f, 0.4f, false);
    }
}