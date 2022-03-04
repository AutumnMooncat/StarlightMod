package Starlight.monsters;

import Starlight.TheStarsAboveMod;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.CurlUpPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

public class LouseMetal extends CustomMonster {
    public static final String ID = TheStarsAboveMod.makeID(LouseMetal.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private static final int HP_MIN = 30;
    private static final int HP_MAX = 55;
    private static final int A_2_HP_MIN = 35;
    private static final int A_2_HP_MAX = 60;
    private static final int STR_AMOUNT = 2;
    private static final byte BITE = 3;
    private static final byte STRENGTHEN = 4;

    private boolean isOpen = true;
    private static final String CLOSED_STATE = "CLOSED";
    private static final String OPEN_STATE = "OPEN";
    private static final String REAR_IDLE = "REAR_IDLE";
    private int biteDamage;

    public LouseMetal(float x, float y) {
        super(NAME, ID, 15, 0.0F, -5.0F, 180.0F, 140.0F, (String)null, x, y);
        this.loadAnimation("StarlightResources/images/monsters/LouseMetal/skeleton.atlas", "StarlightResources/images/monsters/LouseMetal/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_2_HP_MIN, A_2_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.biteDamage = AbstractDungeon.monsterHpRng.random(6, 8);
        } else {
            this.biteDamage = AbstractDungeon.monsterHpRng.random(5, 7);
        }

        this.damage.add(new DamageInfo(this, this.biteDamage));
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.ascensionLevel >= 17) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(9, 12))));
        } else if (AbstractDungeon.ascensionLevel >= 7) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(4, 8))));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(3, 7))));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 2)));

    }

    public void takeTurn() {
        switch(this.nextMove) {
            case BITE:
                if (!this.isOpen) {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                }

                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            case 4:
                if (!this.isOpen) {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REAR"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(1.2F));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REAR_IDLE"));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.9F));
                }

                if (AbstractDungeon.ascensionLevel >= 17) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, STR_AMOUNT + 1), STR_AMOUNT + 1));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, STR_AMOUNT), STR_AMOUNT));
                }

                if (!this.hasPower(CurlUpPower.POWER_ID)) {
                    if (AbstractDungeon.ascensionLevel >= 17) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(9, 12))));
                    } else if (AbstractDungeon.ascensionLevel >= 7) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(4, 8))));
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(3, 7))));
                    }
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void changeState(String stateName) {
        if (stateName.equals(CLOSED_STATE)) {
            this.state.setAnimation(0, "transitiontoclosed", false);
            this.state.addAnimation(0, "idle closed", true, 0.0F);
            this.isOpen = false;
        } else if (stateName.equals(OPEN_STATE)) {
            this.state.setAnimation(0, "transitiontoopened", false);
            this.state.addAnimation(0, "idle", true, 0.0F);
            this.isOpen = true;
        } else if (stateName.equals(REAR_IDLE)) {
            this.state.setAnimation(0, "rear", false);
            this.state.addAnimation(0, "idle", true, 0.0F);
            this.isOpen = true;
        } else {
            this.state.setAnimation(0, "transitiontoopened", false);
            this.state.addAnimation(0, "rear", false, 0.0F);
            this.state.addAnimation(0, "idle", true, 0.0F);
            this.isOpen = true;
        }

    }

    protected void getMove(int num) {
        if (this.hasPower(ThornsPower.POWER_ID) && this.getPower(ThornsPower.POWER_ID).amount >= 10) {
            this.setMove(BITE, Intent.ATTACK, this.damage.get(0).base);
        }
        if (AbstractDungeon.ascensionLevel >= 17) {
            if (num < 25) {
                if (this.lastMove(STRENGTHEN)) {
                    this.setMove(BITE, Intent.ATTACK, this.damage.get(0).base);
                } else {
                    this.setMove(MOVES[0], STRENGTHEN, Intent.BUFF);
                }
            } else if (this.lastTwoMoves(BITE)) {
                this.setMove(MOVES[0], STRENGTHEN, Intent.BUFF);
            } else {
                this.setMove(BITE, Intent.ATTACK, this.damage.get(0).base);
            }
        } else if (num < 25) {
            if (this.lastTwoMoves(STRENGTHEN)) {
                this.setMove(BITE, Intent.ATTACK, this.damage.get(0).base);
            } else {
                this.setMove(MOVES[0], STRENGTHEN, Intent.BUFF);
            }
        } else if (this.lastTwoMoves(BITE)) {
            this.setMove(MOVES[0], STRENGTHEN, Intent.BUFF);
        } else {
            this.setMove(BITE, Intent.ATTACK, this.damage.get(0).base);
        }

    }
}
