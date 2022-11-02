package Starlight.cards.bookOfFire;

import Starlight.actions.DamageFollowupAction;
import Starlight.actions.ScaleAllByPredAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Consume;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Starlight.TheStarlightMod.makeID;

public class Ember extends AbstractMagickCard {
    public final static String ID = makeID(Ember.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 3;
    private static final int UP_DMG = 4;

    public Ember() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        tags.add(CustomTags.STARLIGHT_FIRE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE, i -> {
            if (i > 0) {
                Wiz.applyToSelfTop(new VigorPower(p, i));
            }
        }));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return Consume.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.985f, 0.65f, 0.45f, 0.55f, false);
    }

}