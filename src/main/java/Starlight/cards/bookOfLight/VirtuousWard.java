package Starlight.cards.bookOfLight;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.ProvidencePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.Sanctity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class VirtuousWard extends AbstractMagickCard {
    public final static String ID = makeID(VirtuousWard.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int BLK = 10;
    private static final int UP_BLK = 2;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 1;

    public VirtuousWard() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        baseMagicNumber = magicNumber = EFFECT;
        exhaust = true;
        selfRetain = true;
        tags.add(CustomTags.STARLIGHT_LIGHT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new ProvidencePower(p, magicNumber));
    }

    public void upp() {
        upgradeBlock(UP_BLK);
        upgradeMagicNumber(UP_EFFECT);
    }

    /*@Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ProvidencePower(p, magicNumber));
    }*/

    @Override
    public String cardArtCopy() {
        return Sanctity.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.5f, 0.63f, 0.54f, 0.54f, false);
    }
}