package Starlight.cards.bookOfFire;

import Starlight.actions.ScaleAllByPredAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class FireArrow extends AbstractMagickCard {
    public final static String ID = makeID(FireArrow.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 7;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 4;
    private static final int UP_EFFECT = 2;

    public FireArrow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_FIRE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        Wiz.atb(new ScaleAllByPredAction(this, magicNumber, ScaleAllByPredAction.StatBoost.DAMAGE, c -> c instanceof FireArrow));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return SearingBlow.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.65f, 0.65f, 0.55f, false);
    }

}