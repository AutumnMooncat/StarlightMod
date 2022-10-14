package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.ProvidencePower;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import Starlight.vfx.AngledFlashAtkImgEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.green.Neutralize;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Starlight.TheStarlightMod.makeID;

public class Balance extends AbstractEasyCard {
    public final static String ID = makeID(Balance.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UP_DAMAGE = 3;
    private static final int BOOST = 3;
    private static final int UP_BOOST = 1;

    public Balance() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = BOOST;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new VFXAction(new AngledFlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, 90f)));
        Wiz.atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true)));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        Wiz.applyToSelf(new ProvidencePower(Wiz.adp(), magicNumber));
    }

    /*public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.magicNumber * countCards();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    private int countCards() {
        return (int) (Wiz.adp().drawPile.group.stream().filter(c -> c.type == CardType.STATUS).count() +
                Wiz.adp().hand.group.stream().filter(c -> c.type == CardType.STATUS).count() +
                Wiz.adp().discardPile.group.stream().filter(c -> c.type == CardType.STATUS).count());
    }*/

    /*@Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
    }*/

    public void upp() {
        upgradeDamage(UP_DAMAGE);
        upgradeMagicNumber(UP_BOOST);
    }

    @Override
    public String cardArtCopy() {
        return Neutralize.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.1f, 0.5f, 0.4f, 0.7f, false);
    }
}