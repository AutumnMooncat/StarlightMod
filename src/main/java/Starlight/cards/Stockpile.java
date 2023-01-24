package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.SpellPower;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.green.Prepared;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Stockpile extends AbstractEasyCard {
    public final static String ID = makeID(Stockpile.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLK = 3;
    private static final int UP_BLK = 1;
    private static final int EFFECT = 6;
    private static final int UP_EFFECT = 3;

    public Stockpile() {
        super(ID, COST, TYPE, RARITY, TARGET);
        //baseBlock = block = BLK;
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //Wiz.applyToSelf(new StockpilePower(p, magicNumber));
        Wiz.applyToSelf(new SpellPower(p, magicNumber));
    }

    public void upp() {
        //upgradeBlock(UP_BLK);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Prepared.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.5f, 0.63f, 0.54f, 0.54f, false);
    }
}