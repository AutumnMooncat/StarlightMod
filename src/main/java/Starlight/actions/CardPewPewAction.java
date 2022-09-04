package Starlight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class CardPewPewAction extends AbstractGameAction {
    AbstractCard card;

    public CardPewPewAction(AbstractCard card, AbstractCreature target) {
        this.card = card;
        this.target = target;
    }

    @Override
    public void update() {
        CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT", 0.3f);
        AbstractDungeon.effectList.add(new SmallLaserEffect(card.hb.cX, card.hb.cY, target.hb.cX, target.hb.cY));
        this.isDone = true;
    }
}
