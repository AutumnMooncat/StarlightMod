package Starlight.cards.bookOfWater;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.purple.Brilliance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Starlight.TheStarlightMod.makeID;

public class Deluge extends AbstractMagickCard {
    public final static String ID = makeID(Deluge.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 11;
    private static final int UP_DMG = 4;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 1;

    private int magicLastFrame;

    public Deluge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        //baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_WATER);
        //exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new SFXAction("BLUNT_FAST"));
        Wiz.atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY + 99f * Settings.scale, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true), 0.15f));
        Wiz.atb(new SFXAction("BLUNT_FAST"));
        Wiz.atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY + 66f * Settings.scale, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true), 0.15f));
        Wiz.atb(new SFXAction("BLUNT_FAST"));
        Wiz.atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY + 33f * Settings.scale, AbstractGameAction.AttackEffect.BLUNT_LIGHT, true), 0.15f));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void triggerOnManualDiscard() {
        Wiz.atb(new NewQueueCardAction(this, true, false, true));
        //AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, false));
        Wiz.adp().discardPile.removeCard(this);
    }

    /*@Override
    public void calculateCardDamage(AbstractMonster mo) {
        magicLastFrame = magicNumber;
        int base = baseDamage;
        baseDamage += (getWet(mo) * magicNumber);
        super.calculateCardDamage(mo);
        baseDamage = base;
        isDamageModified = damage != baseDamage;
        if (magicLastFrame != magicNumber) {
            magicLastFrame = magicNumber;
            calculateCardDamage(mo);
        }
        if (magicLastFrame != magicNumber) {
            magicLastFrame = magicNumber;
            applyPowers();
        }
    }

    private int getWet(AbstractCreature c) {
        if (c.hasPower(WetPower.POWER_ID)) {
            return c.getPower(WetPower.POWER_ID).amount;
        }
        return 0;
    }*/

    public void upp() {
        upgradeDamage(UP_DMG);
        //upgradeMagicNumber(UP_EFFECT);
        //exhaust = false;
        //uDesc();
    }

    @Override
    public String cardArtCopy() {
        return Brilliance.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.48f, 0.48f, 0.59f, 0.55f, false);
    }
}