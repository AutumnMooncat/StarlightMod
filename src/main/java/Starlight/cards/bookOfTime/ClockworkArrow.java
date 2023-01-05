package Starlight.cards.bookOfTime;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.green.Skewer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class ClockworkArrow extends AbstractMagickCard {
    public final static String ID = makeID(ClockworkArrow.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 12;
    private static final int UP_DMG = 4;
    private static final int EFFECT = 6;
    private static final int UP_EFFECT = 2;

    public ClockworkArrow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        tags.add(CustomTags.STARLIGHT_TIME);
        tags.add(CustomTags.STARLIGHT_ARROW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        addToBot(new ReduceCostAction(this.uuid, 1));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ClockworkArrow.this.costForTurn = 0;
                ClockworkArrow.this.cost = 0;
                ClockworkArrow.this.isCostModified = true;
                this.isDone = true;
            }
        });
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    /*@Override
    public void triggerOnExhaust() {
        AbstractCard card = this.makeStatEquivalentCopy();
        if (card.cost > 0) {
            card.cost = 0;
            card.costForTurn = 0;
            card.isCostModified = true;
        }
        Wiz.makeInHand(card);
    }*/

    @Override
    public String cardArtCopy() {
        return Skewer.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.10f, 0.65f, 0.55f, false);
    }

}