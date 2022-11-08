
package Starlight.cards.bookOfSpace;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.SpellPower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.purple.Conclude;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

import static Starlight.TheStarlightMod.makeID;

public class Gravity extends AbstractMagickCard {
    public final static String ID = makeID(Gravity.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 12;
    private static final int UP_DMG = 4;
    private static final int SCALE = 2;
    private static final int UP_SCALE = 1;

    private static final int maxIterations = 25;

    public Gravity() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = SCALE;
        isMultiDamage = true;
        tags.add(CustomTags.STARLIGHT_SPACE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AbstractGameAction() {
            boolean firstPass = true;
            float iterations;
            @Override
            public void update() {
                if (firstPass) {
                    Wiz.forAllMonstersLiving(mon -> {
                        mon.flipVertical = !mon.flipVertical;
                    });
                    CardCrawlGame.sound.playA("APPEAR", 1.1f);
                    firstPass = false;
                }
                iterations++;
                float dist = 10f * Settings.scale * iterations;
                Wiz.forAllMonstersLiving(mon -> {
                    mon.drawY += dist;
                    mon.hb.move(mon.hb.cX, mon.hb.cY + dist);
                });
                this.isDone = iterations == maxIterations;
                if (isDone) {
                    /*Wiz.forAllMonstersLiving(mon -> {
                        if (mon.hasPower(FlightPower.POWER_ID)) {
                            Wiz.att(new RemoveSpecificPowerAction(mon, p, FlightPower.POWER_ID));
                        }
                    });*/
                }
            }
        });
        Wiz.atb(new AbstractGameAction() {
            boolean firstPass = true;
            float iterations;
            @Override
            public void update() {
                if (firstPass) {
                    /*Wiz.forAllMonstersLiving(mon -> {
                        mon.flipVertical = !mon.flipVertical;
                    });*/
                    firstPass = false;
                    CardCrawlGame.sound.playA("APPEAR", 0.8f);
                }
                iterations++;
                float dist = 10f * Settings.scale * iterations;
                Wiz.forAllMonstersLiving(mon -> {
                    mon.drawY -= dist;
                    mon.hb.move(mon.hb.cX, mon.hb.cY - dist);
                });
                this.isDone = iterations == maxIterations;
                if (isDone) {
                    //CardCrawlGame.sound.playA("GHOST_ORB_IGNITE_1", 0.8f);
                    Wiz.forAllMonstersLiving(mon -> {
                        mon.flipVertical = !mon.flipVertical;
                        AbstractDungeon.effectList.add(new ExplosionSmallEffect(mon.hb.cX, mon.hb.cY));
                    });
                    CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT, false);
                    //CardCrawlGame.sound.playA("BLUNT_HEAVY", 0.8f);
                    CardCrawlGame.sound.play("CEILING_BOOM_1", 0.8f);
                    Wiz.att(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AttackEffect.NONE, true));
                }
            }
        });
    }

    public void applyPowers() {
        AbstractPower SP = AbstractDungeon.player.getPower(SpellPower.POWER_ID);
        if (SP != null) {
            SP.amount *= this.magicNumber;
        }

        super.applyPowers();
        if (SP != null) {
            SP.amount /= this.magicNumber;
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower SP = AbstractDungeon.player.getPower(SpellPower.POWER_ID);
        if (SP != null) {
            SP.amount *= this.magicNumber;
        }

        super.calculateCardDamage(mo);
        if (SP != null) {
            SP.amount /= this.magicNumber;
        }
    }


    public void upp() {
        //upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_SCALE);
    }

    @Override
    public String cardArtCopy() {
        return Conclude.ID;
    }

}