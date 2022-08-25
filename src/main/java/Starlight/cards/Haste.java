package Starlight.cards;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

import static Starlight.TheStarlightMod.makeID;

public class Haste extends AbstractMagickCard {
    public final static String ID = makeID(Haste.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int BLK = 4;
    private static final int UP_BLK = 3;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Haste() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new EnergizedBluePower(p, magicNumber));
        Wiz.applyToSelf(new DrawCardNextTurnPower(p, magicNumber));
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
    }

    public void upp() {
        upgradeBlock(UP_BLK);
        //upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Blur.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.58f, 0.57f, 0.57f, 0.60f, false);
    }
}