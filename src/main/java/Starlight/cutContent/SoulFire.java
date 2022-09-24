package Starlight.cutContent;

import Starlight.actions.PlayRandomCardAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class SoulFire extends AbstractEasyCard {
    public final static String ID = makeID(SoulFire.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.STATUS;

    private static final int STR = 2;
    private static final int UP_STR = 1;

    public SoulFire() {
        super(ID, -2, TYPE, RARITY, TARGET, CardColor.COLORLESS);
        baseMagicNumber = magicNumber = STR;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new PlayRandomCardAction(p.drawPile, 1, c -> c instanceof AbstractMagickCard));
    }

    public void upp() {
        upgradeMagicNumber(UP_STR);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.8f, 0.5f, 0.5f, 0.5f, false);
    }
}