package Starlight.cards.bookOfTime;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.DustToDustPower;
import Starlight.powers.RuinPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class DustToDust extends AbstractMagickCard {
    public final static String ID = makeID(DustToDust.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 7;
    private static final int UP_EFFECT = 2;
    private static final int TURNS = 1;
    private static final int UP_TURNS = 1;

    public DustToDust() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = TURNS;
        tags.add(CustomTags.STARLIGHT_TIME);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        /*Wiz.atb(new ExhaustByPredAction(p.hand, (int) p.hand.group.stream().filter(c -> c != this).count(), true, c -> true, new AbstractGameAction() {
            @Override
            public void update() {
                if (!ExhaustByPredAction.exhaustedCards.isEmpty()) {
                    Wiz.forAllMonstersLiving(mon -> {
                        Wiz.applyToEnemyTop(mon, new RuinPower(mon, p, magicNumber * ExhaustByPredAction.exhaustedCards.size()));
                    });
                    Wiz.att(new SFXAction("ATTACK_WHIFF_1", 0.2f));
                }
                this.isDone = true;
            }
        }));*/
        Wiz.applyToEnemy(m, new RuinPower(m, p, magicNumber));
        Wiz.applyToEnemy(m, new DustToDustPower(m, secondMagic));
    }

    public void upp() {
        //upgradeMagicNumber(UP_EFFECT);
        upgradeSecondMagic(UP_TURNS);
    }

    @Override
    public String cardArtCopy() {
        return Decay.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.58f, 0.57f, 0.57f, 0.60f, false);
    }
}