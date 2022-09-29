package Starlight.cards;

import Starlight.cardmods.ResonantMod;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.purple.EmptyFist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class PowerFist extends AbstractEasyCard {
    public final static String ID = makeID(PowerFist.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 8;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public PowerFist() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        CardModifierManager.addModifier(this, new ResonantMod(true));
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
        return (int) (Wiz.adp().drawPile.group.stream().filter(c -> c != this && c instanceof PowerFist).count() +
                Wiz.adp().hand.group.stream().filter(c -> c != this && c instanceof PowerFist).count() +
                Wiz.adp().discardPile.group.stream().filter(c -> c != this && c instanceof PowerFist).count());
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return EmptyFist.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.15f, 0.5f, 0.5f, 0.5f, false);
    }
}