package Starlight.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class SpearWaveEffect extends AbstractGameEffect {
    private static final float DUR = 0.3f;
    private static final int MAX_DAGGERS = 20;
    private int daggers;

    @Override
    public void update() {
        this.duration += Gdx.graphics.getDeltaTime();
        if (duration >= DUR/MAX_DAGGERS) {
            float angle = MathUtils.random(-80, -70f);
            float x = daggers * MathUtils.random(80,100) * Settings.scale;
            AbstractDungeon.effectsQueue.add(new ColoredFlyingDaggerEffect(AbstractDungeon.player.hb.cX + x, Settings.HEIGHT, angle, false, Settings.GOLD_COLOR));
            AbstractDungeon.effectsQueue.add(new ColoredFlyingDaggerEffect(AbstractDungeon.player.hb.cX - x, Settings.HEIGHT, 180f - angle, false, Settings.GOLD_COLOR));
            daggers++;
        }
        if (daggers >= MAX_DAGGERS) {
            isDone = true;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {}

    @Override
    public void dispose() {}
}
