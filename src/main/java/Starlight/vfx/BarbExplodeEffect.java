package Starlight.vfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingDaggerEffect;

public class BarbExplodeEffect extends AbstractGameEffect {

    @Override
    public void update() {
        for (int i = 0 ; i < 20 ; i++) {
            float angle = MathUtils.random(10f, 170f);
            float length = MathUtils.random(0, 0);
            float x = AbstractDungeon.player.hb.cX + length * MathUtils.sinDeg(angle);
            float y = AbstractDungeon.player.hb.cY + length * MathUtils.cosDeg(angle);
            AbstractDungeon.effectsQueue.add(new FlyingDaggerEffect(x, y, angle, false));
        }
        isDone = true;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {}

    @Override
    public void dispose() {}
}
