package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.status.Grit;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.red.Impervious;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Moxie extends AbstractEasyCard {
    public final static String ID = makeID(Moxie.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLK = 12;
    private static final int UP_BLK = 4;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Moxie() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        cardsToPreview = new Grit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.atb(new DrawCardAction(magicNumber));
        Wiz.makeInHand(cardsToPreview.makeStatEquivalentCopy());
    }

    public void upp() {
        upgradeBlock(UP_BLK);
    }

    @Override
    public String cardArtCopy() {
        return Impervious.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.99f, 0.5f, 0.5f, 0.5f, false);
    }
}