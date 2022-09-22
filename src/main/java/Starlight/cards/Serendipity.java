package Starlight.cards;

import Starlight.actions.SwapAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.characters.StarlightSisters;
import Starlight.powers.ProvidencePower;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.InnerPeace;
import com.megacrit.cardcrawl.cards.purple.Sanctity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static Starlight.TheStarlightMod.makeID;

public class Serendipity extends AbstractEasyCard {
    public final static String ID = makeID(Serendipity.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int BLK = 4;
    private static final int UP_BLK = 3;

    public Serendipity() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (p instanceof StarlightSisters) {
            Wiz.atb(new SwapAction());
        }
        //Wiz.applyToSelf(new NextTurnBlockPower(p, block));
    }

    public void upp() {
        upgradeBlock(UP_BLK);
    }

    /*@Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new NextTurnBlockPower(p, block));
    }*/

    @Override
    public String cardArtCopy() {
        return InnerPeace.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.61f, 0.63f, 0.54f, 0.54f, false);
    }
}