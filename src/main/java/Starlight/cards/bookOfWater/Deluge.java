package Starlight.cards.bookOfWater;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.purple.Brilliance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Starlight.TheStarlightMod.makeID;

public class Deluge extends AbstractMagickCard {
    public final static String ID = makeID(Deluge.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 6;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    private int magicLastFrame;

    public Deluge() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        baseInfo = info = 0;
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
        //The actual call on play counts this card too, which we don't want
        damage -= magicNumber;
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void applyPowers() {
        baseInfo = info = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        magicLastFrame = magicNumber;
        int base = baseDamage;
        baseDamage += (info * magicNumber);
        super.applyPowers();
        baseDamage = base;
        isDamageModified = damage != baseDamage;
        if (magicLastFrame != magicNumber) {
            magicLastFrame = magicNumber;
            applyPowers();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        baseInfo = info = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        magicLastFrame = magicNumber;
        int base = baseDamage;
        baseDamage += (info * magicNumber);
        super.calculateCardDamage(mo);
        baseDamage = base;
        isDamageModified = damage != baseDamage;
        if (magicLastFrame != magicNumber) {
            magicLastFrame = magicNumber;
            calculateCardDamage(mo);
        }
    }

    public void upp() {
        //upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
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