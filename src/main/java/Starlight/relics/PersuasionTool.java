package Starlight.relics;

import Starlight.cardmods.ForcedMagicMod;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.characters.StarlightSisters;
import Starlight.powers.SpellPower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import static Starlight.TheStarlightMod.makeID;

public class PersuasionTool extends AbstractEasyRelic {
    public static final String ID = makeID(PersuasionTool.class.getSimpleName());
    private static final int CARDS = 2;

    private boolean cardsSelected = true;
    private int selection = 2;

    public PersuasionTool() {
        super(ID, RelicTier.RARE, LandingSound.HEAVY, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARDS + DESCRIPTIONS[1];
    }

    public void onEquip() {
        this.cardsSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.ATTACK && !Wiz.isMagic(c))
                tmp.addToTop(c);
        }
        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
        } else {
            selection = Math.min(2, tmp.size());
            AbstractDungeon.gridSelectScreen.open(tmp, selection, DESCRIPTIONS[2], false, false, false, false);
        }
    }

    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == selection) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            CardModifierManager.addModifier(c, new ForcedMagicMod());
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH/3f, Settings.HEIGHT/2f));
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 2) {
                c = AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                CardModifierManager.addModifier(c, new ForcedMagicMod());
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH*2/3f, Settings.HEIGHT/2f));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.cardsSelected = true;
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    public boolean canSpawn() {
        return Wiz.adp().masterDeck.group.stream().filter(c -> c.type == AbstractCard.CardType.ATTACK && !Wiz.isMagic(c)).count() >= 2;
    }
}
