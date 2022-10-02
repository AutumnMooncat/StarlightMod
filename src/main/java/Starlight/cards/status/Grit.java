package Starlight.cards.status;

import Starlight.actions.PlayRandomCardAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Intimidate;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Grit extends AbstractEasyCard {
    public final static String ID = makeID(Grit.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.STATUS;

    private static final int COST = 1;

    public Grit() {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new PlayRandomCardAction(p.drawPile, 1, c -> true, cards -> {
            for (AbstractCard c : cards) {
                c.exhaustOnUseOnce = true;
            }
        }));
    }

    public void upp() {}

    @Override
    public String cardArtCopy() {
        return Intimidate.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.99f, 0.5f, 0.5f, 0.5f, false);
    }
}