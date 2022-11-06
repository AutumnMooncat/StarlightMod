package Starlight.cards.bookOfLight;

import Starlight.actions.ExhaustByPredAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.StricturePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.blue.Darkness;
import com.megacrit.cardcrawl.cards.purple.Prostrate;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Tribute extends AbstractMagickCard {
    public final static String ID = makeID(Tribute.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Tribute() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_LIGHT);
        exhaust = true;
        cardsToPreview = new Miracle();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ExhaustByPredAction(Wiz.adp().hand, 1, c -> true, new AbstractGameAction() {
            @Override
            public void update() {
                if (!ExhaustByPredAction.exhaustedCards.isEmpty()) {
                    Wiz.makeInHand(new Miracle(), magicNumber);
                }
                this.isDone = true;
            }
        }));
    }

    public void upp() {
        exhaust = false;
        uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Prostrate.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.55f, 0.65f, 0.4f, false);
    }
}