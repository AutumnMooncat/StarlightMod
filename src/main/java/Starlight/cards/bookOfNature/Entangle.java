package Starlight.cards.bookOfNature;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.TanglePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.red.Disarm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Entangle extends AbstractMagickCard {
    public final static String ID = makeID(Entangle.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int BLK = 4;
    private static final int UP_BLK = 2;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 1;

    public Entangle() {
        super(ID, COST, TYPE, RARITY, TARGET);
        //baseBlock = block = BLK;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_NATURE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //blck();
        Wiz.applyToEnemy(m, new TanglePower(m, magicNumber));
    }

    public void upp() {
        //upgradeBlock(UP_BLK);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Disarm.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.85f, 0.55f, 0.55f, 0.45f, false);
    }
}