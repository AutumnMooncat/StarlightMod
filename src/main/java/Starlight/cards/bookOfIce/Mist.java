package Starlight.cards.bookOfIce;

import Starlight.cards.Anticipate;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.Vigilance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Starlight.TheStarlightMod.makeID;

public class Mist extends AbstractMagickCard {
    public final static String ID = makeID(Mist.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLK = 10;
    private static final int UP_BLK = 4;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Mist() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        baseMagicNumber = magicNumber = EFFECT;
        exhaust = true;
        tags.add(CustomTags.STARLIGHT_ICE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new ArtifactPower(p, magicNumber));
    }

    public void upp() {
        upgradeBlock(UP_BLK);
    }

    @Override
    public String cardArtCopy() {
        return Vigilance.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.65f, 0.65f, 0.55f, false);
    }
}