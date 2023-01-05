package Starlight.cards.bookOfSpace;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import com.megacrit.cardcrawl.cards.purple.Collect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Stardust extends AbstractMagickCard {
    public final static String ID = makeID(Stardust.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;
    private static final int BLK = 8;
    private static final int UP_BLK = 2;

    private int magicLastFrame;

    public Stardust() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = magicLastFrame = EFFECT;
        baseBlock = block = BLK;
        tags.add(CustomTags.STARLIGHT_SPACE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UP_BLK);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        magicLastFrame = magicNumber;
        super.calculateCardDamage(mo);
        if (magicLastFrame != magicNumber) {
            magicLastFrame = magicNumber;
            calculateCardDamage(mo);
        }
    }

    @Override
    public void applyPowers() {
        magicLastFrame = magicNumber;
        super.applyPowers();
        if (magicLastFrame != magicNumber) {
            magicLastFrame = magicNumber;
            applyPowers();
        }
    }

    @Override
    protected void applyPowersToBlock() {
        int base = baseBlock;
        baseBlock += magicNumber * ProjectedCardManager.cards.size();
        super.applyPowersToBlock();
        baseBlock = base;
        isBlockModified = baseBlock != block;
    }

    @Override
    public String cardArtCopy() {
        return Collect.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.5f, 0.54f, 0.5f, false);
    }
}