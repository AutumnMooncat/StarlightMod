
package Starlight.cards.bookOfSpace;

import Starlight.actions.GravityVFXAction;
import Starlight.actions.PlayProjectedCardsAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.purple.Conclude;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Gravity extends AbstractMagickCard {
    public final static String ID = makeID(Gravity.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 15;
    private static final int UP_DMG = 5;
    private static final int SCALE = 2;
    private static final int UP_SCALE = 1;

    public Gravity() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        //baseMagicNumber = magicNumber = SCALE;
        isMultiDamage = true;
        tags.add(CustomTags.STARLIGHT_SPACE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GravityVFXAction());
        Wiz.atb(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
        Wiz.atb(new PlayProjectedCardsAction());
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        //upgradeMagicNumber(UP_SCALE);
    }

    @Override
    public String cardArtCopy() {
        return Conclude.ID;
    }

}