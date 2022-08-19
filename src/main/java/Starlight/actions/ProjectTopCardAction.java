package Starlight.actions;

import Starlight.TheStarlightMod;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ProjectTopCardAction extends AbstractGameAction {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("Project")).TEXT;

    public ProjectTopCardAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        if (Wiz.adp().drawPile.isEmpty()) {
            this.isDone = true;
            return;
        }
        if (amount > Wiz.adp().drawPile.size()) {
            amount = Wiz.adp().drawPile.size();
        }
        for (int i = 0 ; i < amount ; i++) {
            AbstractCard c = Wiz.adp().drawPile.getTopCard();
            ProjectedCardManager.addCard(c);
            Wiz.adp().drawPile.removeCard(c);
        }
        this.isDone = true;
    }
}
