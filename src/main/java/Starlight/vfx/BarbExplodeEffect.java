package Starlight.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingDaggerEffect;

public class BarbExplodeEffect extends AbstractGameEffect {
    private static final float DUR = 0.3f;
    private static final int MAX_DAGGERS = 20;
    private int daggers;

    @Override
    public void update() {
        this.duration += Gdx.graphics.getDeltaTime();
        if (duration >= DUR/MAX_DAGGERS) {
            float angle = MathUtils.random(-10f, 190f);
            float length = MathUtils.random(0, 0);
            float x = AbstractDungeon.player.hb.cX + length * MathUtils.sinDeg(angle);
            float y = AbstractDungeon.player.hb.cY + length * MathUtils.cosDeg(angle);
            AbstractDungeon.effectsQueue.add(new FlyingDaggerEffect(x, y, angle, false));
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
