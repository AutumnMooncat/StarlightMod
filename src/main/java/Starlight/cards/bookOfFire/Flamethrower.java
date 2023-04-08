package Starlight.cards.bookOfFire;

import Starlight.actions.DamageFollowupAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.BurnPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.cards.purple.ReachHeaven;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Starlight.TheStarlightMod.makeID;

public class Flamethrower extends AbstractMagickCard {
    public final static String ID = makeID(Flamethrower.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 8;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 4;
    private static final int UP_EFFECT = 1;

    public Flamethrower() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        //baseMagicNumber = magicNumber = EFFECT;
        exhaust = true;
        tags.add(CustomTags.STARLIGHT_FIRE);
        tags.add(CustomTags.STARLIGHT_APPLIES_BURN);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new SFXAction("ATTACK_FIRE"));
        Wiz.atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY + 20f * Settings.scale, AbstractGameAction.AttackEffect.FIRE, true), 0.2f));
        Wiz.atb(new SFXAction("ATTACK_FIRE"));
        Wiz.atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX + 20f * Settings.scale, m.hb.cY, AbstractGameAction.AttackEffect.FIRE, true), 0.2f));
        Wiz.atb(new SFXAction("ATTACK_FIRE"));
        Wiz.atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY - 20f * Settings.scale, AbstractGameAction.AttackEffect.FIRE, true), 0.2f));
        Wiz.atb(new SFXAction("ATTACK_FIRE"));
        Wiz.atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX - 20f * Settings.scale, m.hb.cY, AbstractGameAction.AttackEffect.FIRE, true), 0.0f));
        Wiz.atb(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE, t -> {
            if (t.lastDamageTaken > 0) {
                Wiz.applyToEnemy(m, new BurnPower(t, p, t.lastDamageTaken));
            }
        }));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return ReachHeaven.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.65f, 0.45f, 0.45f, false);
    }

}