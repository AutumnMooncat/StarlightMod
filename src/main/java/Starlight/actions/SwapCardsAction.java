package Starlight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SwapCardsAction extends AbstractGameAction {
    private AbstractCard toReplace;
    private AbstractCard newCard;

    public SwapCardsAction(AbstractCard toReplace, AbstractCard newCard) {
        this.actionType = ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_MED;
        this.toReplace = toReplace;
        this.newCard = newCard;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int index = 0;
        boolean found = false;
        for (AbstractCard card : p.hand.group)
        {
            if (card == toReplace) {
                found = true;
                break;
            }
            index++;
        }
        if(found && toReplace != null) {
            newCard.cardsToPreview = toReplace.makeStatEquivalentCopy();
            newCard.applyPowers();
            newCard.cardsToPreview.applyPowers();
            if (AbstractDungeon.player.hoveredCard == toReplace) {
                AbstractDungeon.player.releaseCard();
            }
            AbstractDungeon.actionManager.cardQueue.removeIf(q -> q.card == toReplace);
            this.addToTop(new UpdateAfterTransformAction(newCard));
            this.addToTop(new TransformCardInHandAction(index, newCard));
        }
        this.isDone = true;
    }
}