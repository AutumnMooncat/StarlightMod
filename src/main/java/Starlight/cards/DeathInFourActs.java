package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.Wiz;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarsAboveMod.makeID;

public class DeathInFourActs extends AbstractEasyCard implements CustomSavable<Integer> {
    public final static String ID = makeID("DeathInFourActs");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public DeathInFourActs() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = damage = 7;
        baseInfo = info = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        useShot();
    }

    public void useShot() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                info++;
                if (info == 5) {
                    info = 1;
                }
                initializeDescription();
                for (AbstractCard c : Wiz.adp().masterDeck.group) {
                    if (uuid.equals(c.uuid) && c instanceof AbstractEasyCard) {
                        ((AbstractEasyCard) c).info++;
                        if (((AbstractEasyCard) c).info == 5) {
                            ((AbstractEasyCard) c).info = 1;
                        }
                        c.initializeDescription();
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (info == 4) {
            damage *= 3;
            isDamageModified = damage != baseDamage;
            exhaust = true;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(info == 4) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upp() {
        upgradeDamage(3);
    }

    @Override
    public void resetAttributes() {
        int count = info;
        super.resetAttributes();
        info = count;
        isInfoModified = info != baseInfo;
    }

    @Override
    public Integer onSave() {
        return info;
    }

    @Override
    public void onLoad(Integer integer) {
        if (integer != null) {
            this.info = integer;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        DeathInFourActs copy = (DeathInFourActs) super.makeStatEquivalentCopy();
        copy.info = this.info;
        copy.initializeDescription();
        return copy;
    }
}