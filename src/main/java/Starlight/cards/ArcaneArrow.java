package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.green.Skewer;
import com.megacrit.cardcrawl.cards.purple.EmptyFist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class ArcaneArrow extends AbstractEasyCard {
    public final static String ID = makeID(ArcaneArrow.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 8;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public ArcaneArrow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_ARROW);
        //CardModifierManager.addModifier(this, new ResonantMod(true));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    public void calculateCardDamage(AbstractMonster mo) {
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
        return (int) (Wiz.adp().drawPile.group.stream().filter(c -> c instanceof ArcaneArrow).count() +
                Wiz.adp().hand.group.stream().filter(c -> c instanceof ArcaneArrow).count() +
                Wiz.adp().discardPile.group.stream().filter(c -> c instanceof ArcaneArrow).count() +
                ProjectedCardManager.cards.group.stream().filter(c -> c instanceof ArcaneArrow).count());
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Skewer.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.25f, 0.55f, 0.65f, 0.5f, false);
    }
}