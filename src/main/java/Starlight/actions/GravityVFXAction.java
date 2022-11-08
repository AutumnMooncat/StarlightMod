package Starlight.actions;

import Starlight.util.Wiz;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

public class GravityVFXAction extends AbstractGameAction {
    private static final int maxIterations = 25;
    boolean firstPassThisPhase = true;
    float iterations;
    int actionPhase = 0;
    float delayTimer = 0;

    public GravityVFXAction() {
    }

    @Override
    public void update() {
        if (delayTimer > 0f) {
            delayTimer -= Gdx.graphics.getDeltaTime();
            return;
        }

        if (actionPhase == 0) {
            if (firstPassThisPhase) {
                Wiz.forAllMonstersLiving(mon -> {
                    mon.flipVertical = !mon.flipVertical;
                });
                CardCrawlGame.sound.playA("APPEAR", 1.1f);
                firstPassThisPhase = false;
            }
            iterations++;
            float dist = 10f * Settings.scale * iterations;
            Wiz.forAllMonstersLiving(mon -> {
                mon.drawY += dist;
                mon.hb.move(mon.hb.cX, mon.hb.cY + dist);
            });
            if (iterations == maxIterations) {
                iterations = 0;
                firstPassThisPhase = true;
                actionPhase++;
                delayTimer = 0.5f;
            }
        } else if (actionPhase == 1) {
            if (firstPassThisPhase) {
                Wiz.forAllMonstersLiving(mon -> {
                    mon.flipVertical = !mon.flipVertical;
                });
                firstPassThisPhase = false;
                CardCrawlGame.sound.playA("APPEAR", 0.8f);
            }
            iterations++;
            float dist = 10f * Settings.scale * iterations;
            Wiz.forAllMonstersLiving(mon -> {
                mon.drawY -= dist;
                mon.hb.move(mon.hb.cX, mon.hb.cY - dist);
            });
            this.isDone = iterations == maxIterations;
        }

        if (isDone) {
            //CardCrawlGame.sound.playA("GHOST_ORB_IGNITE_1", 0.8f);
            Wiz.forAllMonstersLiving(mon -> {
                //mon.flipVertical = !mon.flipVertical;
                AbstractDungeon.effectList.add(new ExplosionSmallEffect(mon.hb.cX, mon.hb.cY));
            });
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.SHORT, false);
            //CardCrawlGame.sound.playA("BLUNT_HEAVY", 0.8f);
            CardCrawlGame.sound.play("CEILING_BOOM_1", 0.8f);
        }

    }
}
