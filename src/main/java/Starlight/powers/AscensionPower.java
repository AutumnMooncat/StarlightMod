package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.interfaces.MagicMagicModPower;
import Starlight.util.LoopingSoundManager;
import Starlight.util.Wiz;
import Starlight.vfx.AscensionAuraEffect;
import Starlight.vfx.AscensionEffect;
import basemod.Pair;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.UncommonPotionParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.SilentGainPowerEffect;

import java.util.ArrayList;
import java.util.Random;

public class AscensionPower extends AbstractPower implements MagicMagicModPower {

    public static final String POWER_ID = TheStarlightMod.makeID(AscensionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private float flashTimer;
    private float auraTimer;
    private final ArrayList<AbstractGameEffect> array;
    private static final Random rng = new Random();
    private float particleTimer;
    private static final float PARTICLE_INTERVAL = 0.1f;
    private static Pair<String, Long> loopKey;
    private static String loopSFX = "STANCE_LOOP_WRATH";
    private static String enterSFX = "STANCE_ENTER_CALM";

    public AscensionPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("flight");
        updateDescription();
        this.isTurnBased = true;
        this.priority = 101;
        array = ReflectionHacks.getPrivateInherited(this, ProvidencePower.class, "effect");
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        if (loopKey != null) {
            this.stopIdleSfx();
        }
        CardCrawlGame.sound.play(enterSFX);
        loopKey = LoopingSoundManager.addLoopedSound(loopSFX);
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
        AbstractDungeon.effectList.add(new AscensionEffect());
    }

    @Override
    public void onRemove() {
        super.onRemove();
        this.stopIdleSfx();
    }

    public void stopIdleSfx() {
        if (loopKey != null) {
            LoopingSoundManager.stopLoopedSound(loopKey);
        }
    }

    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        if (Wiz.isMagic(card)) {
            return blockAmount * 2f;
        }
        return blockAmount;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (Wiz.isMagic(card)) {
            return damage * 2f;
        }
        return damage;
    }

    @Override
    public void atEndOfRound() {
        Wiz.atb(new ReducePowerAction(owner, owner, this , 1));
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        flashTimer += Gdx.graphics.getDeltaTime();
        if (flashTimer > 1f) {
            array.add(new SilentGainPowerEffect(this));
            flashTimer = 0f;
        }
    }

    @Override
    public void updateParticles() {
        this.particleTimer -= Gdx.graphics.getRawDeltaTime();
        this.auraTimer -= Gdx.graphics.getRawDeltaTime();
        if (this.auraTimer < 0.0F) {
            this.auraTimer = MathUtils.random(0.45F, 0.55F);
            AbstractDungeon.effectsQueue.add(new AscensionAuraEffect());
        }
        if (this.particleTimer < 0.0F) {
            float xOff = ((owner.hb_w) * (float) rng.nextGaussian())*0.25f;
            if(MathUtils.randomBoolean()) {
                xOff = -xOff;
            }
            float yOff = ((owner.hb_w) * (float) rng.nextGaussian())*0.25f;
            if(MathUtils.randomBoolean()) {
                yOff = -yOff;
            }
            //AbstractDungeon.effectList.add(new StraightFireParticle(owner.drawX + xOff, owner.drawY + MathUtils.random(owner.hb_h/2f), 75f));
            AbstractDungeon.effectList.add(new UncommonPotionParticleEffect(owner.hb.cX+xOff, owner.hb.cY+yOff));
            this.particleTimer = PARTICLE_INTERVAL;
        }
    }

    @Override
    public float modifyMagicMagic(float magic, AbstractCard magicCard) {
        return magic * 2;
    }
}
