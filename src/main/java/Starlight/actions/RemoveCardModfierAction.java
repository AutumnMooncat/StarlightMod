package Starlight.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class RemoveCardModfierAction extends AbstractGameAction {
    private String modID;
    private AbstractCardModifier mod;
    private AbstractCard card;
    private boolean removeInherent;

    public RemoveCardModfierAction(AbstractCard card, AbstractCardModifier mod) {
        this(card, mod, false);
    }

    public RemoveCardModfierAction(AbstractCard card, AbstractCardModifier mod, boolean removeInherent) {
        this.mod = mod;
        this.card = card;
        this.removeInherent = removeInherent;
    }

    public RemoveCardModfierAction(AbstractCard card, String modID) {
        this(card, modID, false);
    }

    public RemoveCardModfierAction(AbstractCard card, String modID, boolean removeInherent) {
        this.modID = modID;
        this.card = card;
        this.removeInherent = removeInherent;
    }

    @Override
    public void update() {
        if (mod != null) {
            CardModifierManager.removeSpecificModifier(card, mod, removeInherent);
        } else {
            CardModifierManager.removeModifiersById(card, modID, removeInherent);
        }
        isDone = true;
    }
}
