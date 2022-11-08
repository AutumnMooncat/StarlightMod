package Starlight.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ComplexSFXAction extends AbstractGameAction {
    String key;
    float pitchVar;
    float pitchAdjust;

    public ComplexSFXAction(String key, float pitchVar, float pitchAdjust) {
        this.key = key;
        this.pitchVar = pitchVar;
        this.pitchAdjust = pitchAdjust;
        this.actionType = AbstractGameAction.ActionType.WAIT;
    }

    @Override
    public void update() {
        CardCrawlGame.sound.playA(this.key, this.pitchAdjust + MathUtils.random(-pitchVar, pitchVar));
        this.isDone = true;
    }
}
