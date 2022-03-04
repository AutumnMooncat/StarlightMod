package Starlight.actions;

import Starlight.characters.AbstractDoubleCharacter;
import Starlight.characters.Starfarers;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HotswapPlayerAction extends AbstractGameAction {
    @Override
    public void update() {
        if (AbstractDungeon.player instanceof AbstractDoubleCharacter) {
            ((Starfarers) AbstractDungeon.player).hotswap();
        }
        this.isDone = true;
    }
}
