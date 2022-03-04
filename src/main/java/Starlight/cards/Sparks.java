package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarsAboveMod.makeID;

public class Sparks extends AbstractEasyCard {
    public final static String ID = makeID("Sparks");

    public Sparks() {
        super(ID, -2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.NONE);
        damage = baseDamage = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        if (cardPlayed.type == CardType.ATTACK && Wiz.adp().hand.contains(this)) {
            superFlash();
            addToBot(new DamageAllEnemiesAction(Wiz.adp(), damage, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public void upp() {
        upgradeDamage(1);
    }
}