package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.red.Entrench;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static Starlight.TheStarlightMod.makeID;

public class Supply extends AbstractEasyCard {
    public final static String ID = makeID(Supply.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;
    private static final int BLK = 7;
    private static final int UP_BLK = 3;

    public Supply() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseBlock = block = BLK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.atb(new DrawCardAction(magicNumber));
        Wiz.applyToSelf(new DrawCardNextTurnPower(p, magicNumber));
    }

    public void upp() {
        upgradeBlock(UP_BLK);
        //upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Entrench.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.61f, 0.63f, 0.54f, 0.54f, false);
    }

    /*@Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SpellPower(p, magicNumber));
    }*/
}