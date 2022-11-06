package Starlight.vfx;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.combat.FlyingDaggerEffect;

public class ColoredFlyingDaggerEffect extends FlyingDaggerEffect {
    public ColoredFlyingDaggerEffect(float x, float y, float fAngle, boolean shouldFlip, Color c) {
        super(x, y, fAngle, shouldFlip);
        color.set(c);
        color.a = 0f;
    }
}
