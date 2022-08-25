package Starlight.cards;

import Starlight.actions.ForetellAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.patches.CardCounterPatches;
import Starlight.powers.ProvidencePower;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Recycle;
import com.megacrit.cardcrawl.cards.purple.EmptyBody;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class RealityPassage extends AbstractEasyCard {
    public final static String ID = makeID(RealityPassage.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public RealityPassage() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ForetellAction(p.discardPile, magicNumber));
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                if (CardCounterPatches.cardsForetoldThisCombat > 0) {
                    Wiz.applyToSelfTop(new ProvidencePower(p, CardCounterPatches.cardsForetoldThisCombat));
                }
                this.isDone = true;
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return EmptyBody.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.2f, 0.5f, 0.65f, 1, false);
    }
}