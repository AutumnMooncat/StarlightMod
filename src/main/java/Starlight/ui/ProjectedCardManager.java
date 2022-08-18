package Starlight.ui;

import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.ui.panels.ExhaustPanel;
import com.megacrit.cardcrawl.vfx.BobEffect;
import javassist.CtBehavior;

public class ProjectedCardManager {
    public static final float Y_OFFSET = 70f * Settings.scale;
    public static final float X_OFFSET = 100f * Settings.scale;
    private static final CardGroup cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private static final BobEffect bob = new BobEffect(3.0f * Settings.scale, 3.0f);
    public static AbstractCard hovered;

    public static void render(SpriteBatch sb) {
        for (AbstractCard card : cards.group) {
            if (card != hovered) {
                card.render(sb);
            }
        }
        if (hovered != null) {
            hovered.render(sb);
            TipHelper.renderTipForCard(hovered, sb, hovered.keywords);
        }
    }

    public static void update() {
        bob.update();
        int i = 0;
        hovered = null;
        for (AbstractCard card : cards.group) {
            card.target_y = Wiz.adp().hb.cY + Wiz.adp().hb.height/2f + Y_OFFSET + bob.y;
            card.target_x = Wiz.adp().hb.cX + X_OFFSET * (cards.size()-1) / 2f - X_OFFSET * i;
            card.targetAngle = 0f;
            card.update();
            card.hb.update();
            if (card.hb.hovered && hovered == null) {
                card.targetDrawScale = 0.75f;
                hovered = card;
            } else {
                card.targetDrawScale = 0.2f;
            }
            i++;
        }
    }

    public static void playCards() {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : cards.group) {
                    Wiz.adp().limbo.group.add(card);
                    card.targetDrawScale = 0.75F;
                    card.applyPowers();
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, true, EnergyPanel.getCurrentEnergy(), false, true), false);
                    //Wiz.att(new NewQueueCardAction(card, true, false, true));
                    Wiz.atb(new UnlimboAction(card));
                }
                cards.clear();
                this.isDone = true;
            }
        });
    }

    public static void addCard(AbstractCard card) {
        card.targetAngle = 0f;
        card.beginGlowing();
        cards.addToTop(card);
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "applyStartOfTurnCards")
    public static class PlayCards {
        @SpirePrefixPatch
        public static void playCards() {
            ProjectedCardManager.playCards();
        }
    }

    @SpirePatch2(clz = OverlayMenu.class, method = "render")
    public static class RenderPanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void render(OverlayMenu __instance, SpriteBatch sb) {
            ProjectedCardManager.render(sb);
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ExhaustPanel.class, "render");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "combatUpdate")
    public static class UpdatePile {
        @SpirePostfixPatch
        public static void update(AbstractPlayer __instance) {
            ProjectedCardManager.update();
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "preBattlePrep")
    @SpirePatch2(clz = AbstractPlayer.class, method = "onVictory")
    public static class emptyCards {
        @SpirePostfixPatch
        public static void yeet() {
            cards.clear();
        }
    }
}
