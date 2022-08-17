package Starlight.characters;

import Starlight.CustomAnimationListener;
import Starlight.CustomSpriterAnimation;
import Starlight.RandomChatterHelper;
import Starlight.cards.*;
import Starlight.cards.interfaces.SkillAnimationAttack;
import Starlight.powers.TagTeamPower;
import Starlight.util.Wiz;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.brashmonkey.spriter.Player;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import Starlight.relics.Constellation;

import java.util.ArrayList;

import static Starlight.characters.StarlightSisters.Enums.METEORITE_PURPLE_COLOR;
import static Starlight.TheStarlightMod.*;

public class StarlightSisters extends CustomPlayer {
    private static final String[] orbTextures = {
            modID + "Resources/images/char/mainChar/orb/layer1.png",
            modID + "Resources/images/char/mainChar/orb/layer2.png",
            modID + "Resources/images/char/mainChar/orb/layer3.png",
            modID + "Resources/images/char/mainChar/orb/layer4.png",
            modID + "Resources/images/char/mainChar/orb/layer5.png",
            modID + "Resources/images/char/mainChar/orb/layer6.png",
            modID + "Resources/images/char/mainChar/orb/layer1d.png",
            modID + "Resources/images/char/mainChar/orb/layer2d.png",
            modID + "Resources/images/char/mainChar/orb/layer3d.png",
            modID + "Resources/images/char/mainChar/orb/layer4d.png",
            modID + "Resources/images/char/mainChar/orb/layer5d.png",};
    public static final String ID = makeID("StarlightSisters");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    public static final String[] NAMES = characterStrings.NAMES;
    public static final String[] TEXT = characterStrings.TEXT;

    public boolean attackerInFront = true;

    public StarlightSisters(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, modID + "Resources/images/char/mainChar/orb/vfx.png", null, new CustomSpriterAnimation(
                modID + "Resources/images/char/mainChar/StarlightSisters.scml"));
        Player.PlayerListener listener = new CustomAnimationListener(this);
        ((CustomSpriterAnimation)this.animation).myPlayer.addListener(listener);
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(3));


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                50,
                50,
                0,
                99,
                5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Ambush.ID);
        retVal.add(Fire.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Prepare.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Constellation.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return METEORITE_PURPLE_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return METEORITE_PURPLE.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new StarlightSisters(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return METEORITE_PURPLE.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return METEORITE_PURPLE.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_STARFARERS;
        @SpireEnum(name = "METEORITE_PURPLE_COLOR")
        public static AbstractCard.CardColor METEORITE_PURPLE_COLOR;
        @SpireEnum(name = "METEORITE_PURPLE_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    public CustomSpriterAnimation getAnimation() {
        return (CustomSpriterAnimation) this.animation;
    }

    public void playAnimation(String name) {
        ((CustomSpriterAnimation)this.animation).myPlayer.setAnimation(name);
    }

    public void stopAnimation() {
        CustomSpriterAnimation anim = (CustomSpriterAnimation) this.animation;
        int time = anim.myPlayer.getAnimation().length;
        anim.myPlayer.setTime(time);
        anim.myPlayer.speed = 0;
    }

    public void resetToIdleAnimation() {
        if (attackerInFront) {
            playAnimation("IdleA");
        } else {
            playAnimation("IdleB");
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        if (attackerInFront) {
            playAnimation("HappyA");
        } else {
            playAnimation("HappyB");
        }
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);
        switch (c.type) {
            case ATTACK:
                if (attackerInFront) {
                    playAnimation("Attack");
                } else {
                    playAnimation("AttackSwap");
                    attackerInFront = true;
                }
                RandomChatterHelper.showChatter(RandomChatterHelper.getAttackText(), cardTalkProbability, enableCardBattleTalkEffect);
                break;
            case POWER:
                RandomChatterHelper.showChatter(RandomChatterHelper.getPowerText(), cardTalkProbability, enableCardBattleTalkEffect);
                if (attackerInFront) {
                    playAnimation("HappyA");
                } else {
                    playAnimation("HappyB");
                }
                break;
            case SKILL:
                if (attackerInFront) {
                    playAnimation("SkillSwap");
                    attackerInFront = false;
                } else {
                    playAnimation("Skill");
                }
                RandomChatterHelper.showChatter(RandomChatterHelper.getSkillText(), cardTalkProbability, enableCardBattleTalkEffect);
                break;
            default:
                RandomChatterHelper.showChatter(RandomChatterHelper.getSkillText(), cardTalkProbability, enableCardBattleTalkEffect);
                if (attackerInFront) {
                    playAnimation("CastA");
                } else {
                    playAnimation("CastB");
                }
                break;
        }
    }

    public void damage(DamageInfo info) {
        boolean hadBlockBeforeSuper = this.currentBlock > 0;
        super.damage(info);
        boolean hasBlockAfterSuper = this.currentBlock > 0;
        boolean tookNoDamage = this.lastDamageTaken == 0;
        if (hadBlockBeforeSuper && (hasBlockAfterSuper || tookNoDamage)) {
            RandomChatterHelper.showChatter(RandomChatterHelper.getBlockedDamageText(), damagedTalkProbability, enableDamagedBattleTalkEffect);
            if (attackerInFront) {
                playAnimation("BraceA");
            } else {
                playAnimation("BraceB");
            }
        } else {
            if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
                if (info.output >= 15) {
                    RandomChatterHelper.showChatter(RandomChatterHelper.getHeavyDamageText(), damagedTalkProbability, enableDamagedBattleTalkEffect);
                } else {
                    RandomChatterHelper.showChatter(RandomChatterHelper.getLightDamageText(), damagedTalkProbability, enableDamagedBattleTalkEffect);
                }
            } else if (info.type == DamageInfo.DamageType.THORNS && info.output > 0) {
                RandomChatterHelper.showChatter(RandomChatterHelper.getFieldDamageText(), damagedTalkProbability, enableDamagedBattleTalkEffect);
            }
            if (attackerInFront) {
                playAnimation("HurtA");
            } else {
                playAnimation("HurtB");
            }
        }
    }

    @Override
    public void playDeathAnimation() {
        RandomChatterHelper.showChatter(RandomChatterHelper.getKOText(), preTalkProbability, enablePreBattleTalkEffect); // I don't think this works
        playAnimation("KO");
    }

    @Override
    public void heal(int healAmount) {
        if (healAmount > 0) {
            if (RandomChatterHelper.showChatter(RandomChatterHelper.getHealingText(), damagedTalkProbability, enableDamagedBattleTalkEffect)){ //Technically changes your hp, lol
                if (attackerInFront) {
                    playAnimation("HappyA");
                } else {
                    playAnimation("HappyB");
                }
            }
        }
        super.heal(healAmount);
    }

    @Override
    public void preBattlePrep() {
        attackerInFront = true;
        resetToIdleAnimation();
        super.preBattlePrep();
        Wiz.applyToSelfTop(new TagTeamPower(this));
        boolean bossFight = false;
        for (AbstractMonster mons : AbstractDungeon.getMonsters().monsters) {
            if (mons.type == AbstractMonster.EnemyType.BOSS) {
                bossFight = true;
                break;
            }
        }
        if (AbstractDungeon.getCurrRoom().eliteTrigger || bossFight) {
            RandomChatterHelper.showChatter(RandomChatterHelper.getBossFightText(), preTalkProbability, enablePreBattleTalkEffect);
        } else {
            if (AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth*0.5f) {
                RandomChatterHelper.showChatter(RandomChatterHelper.getLowHPBattleStartText(), preTalkProbability, enablePreBattleTalkEffect);
            } else {
                RandomChatterHelper.showChatter(RandomChatterHelper.getBattleStartText(), preTalkProbability, enablePreBattleTalkEffect);
            }
        }
    }
}
