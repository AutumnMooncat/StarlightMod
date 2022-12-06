package Starlight.cards.bookOfLight;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.JinxPower;
import Starlight.powers.ProvidencePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import Starlight.vfx.SpearWaveEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.green.BladeDance;
import com.megacrit.cardcrawl.cards.purple.SignatureMove;
import com.megacrit.cardcrawl.cards.red.IronWave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;

import static Starlight.TheStarlightMod.makeID;

public class DivineSpears extends AbstractMagickCard {
    public final static String ID = makeID(DivineSpears.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 10;
    private static final int UP_DMG = 3;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public DivineSpears() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        isMultiDamage = true;
        tags.add(CustomTags.STARLIGHT_LIGHT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new VFXAction(new SpearWaveEffect()));
        Wiz.atb(new WaitAction(0.5f));
        //allDmg(AbstractGameAction.AttackEffect.NONE);
        Wiz.atb(new VFXAction(new SpearWaveEffect()));
        Wiz.atb(new WaitAction(0.2f));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        Wiz.atb(new VFXAction(new BorderFlashEffect(Color.GOLD, true), 0.1F));
        Wiz.atb(new SFXAction("HEAL_1"));
        Wiz.atb(new VFXAction(new SanctityEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
        Wiz.applyToSelf(new ProvidencePower(p, magicNumber));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return IronWave.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.55f, 0.55f, 0.50f, false);
    }
}