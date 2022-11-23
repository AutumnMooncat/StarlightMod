package Starlight.patches;

import Starlight.powers.interfaces.RenderOnCardPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class RenderPowerOnCardPatch {


    @SpirePatch(clz = AbstractCard.class, method = "renderEnergy")
    public static class RenderOnCardPatch {
        static ArrayList<AbstractPower> powersToRender = new ArrayList<>();
        @SpirePostfixPatch
        public static void RenderOnCard(AbstractCard __instance, SpriteBatch sb) {
            if (AbstractDungeon.player != null && validLocation(__instance) && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                powersToRender.clear();
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof RenderOnCardPower && ((RenderOnCardPower) p).shouldRender(__instance)) {
                        powersToRender.add(p);
                    }
                }
                renderHelper(sb, powersToRender, __instance);
            }
        }

        //Don't bother rendering if it isn't in one of 4 immediately viewable locations.
        private static boolean validLocation(AbstractCard c) {
            return AbstractDungeon.player.hand.contains(c) /*||
                    AbstractDungeon.player.drawPile.contains(c) ||
                    AbstractDungeon.player.discardPile.contains(c) ||
                    AbstractDungeon.player.exhaustPile.contains(c)*/;
        }

        private static void renderHelper(SpriteBatch sb, ArrayList<AbstractPower> powers, AbstractCard card) {
            float renderScale = 0.65f;
            float ddx = 130f;
            float dx = (powers.size()-1) /2f * ddx / renderScale;
            float dy = -220.0F / renderScale;
            float scale = card.drawScale * Settings.scale * renderScale;
            float w, h, w2, h2;
            TextureAtlas.AtlasRegion img;
            for (AbstractPower p : powers) {
                img = p.region128;
                w = img.packedWidth;
                w2 = w / 2f;
                h = img.packedHeight;
                h2 = h / 2f;
                sb.draw(img, card.current_x - w2 - dx, card.current_y - h2 - dy, w2 + dx, h2 + dy, w, h, scale, scale, card.angle);
                dx -= ddx;
            }
        }

        private static void renderHelper(SpriteBatch sb, TextureAtlas.AtlasRegion img, AbstractCard card) {
            float drawX = card.current_x;
            float drawY = card.current_y;
            sb.setColor(Color.WHITE);
            float dx = 155f;
            float dy = 210f;
            //sb.draw(img, drawX + img.offsetX - (float) img.originalWidth / 2.0F, drawY + img.offsetY - (float) img.originalHeight / 2.0F, (float) img.originalWidth / 2.0F - img.offsetX, (float) img.originalHeight / 2.0F - img.offsetY, (float) img.packedWidth, (float) img.packedHeight, card.drawScale * Settings.scale, card.drawScale * Settings.scale, card.angle);
            sb.draw(img, drawX + dx + img.offsetX - (float) img.originalWidth / 2.0F, drawY + dy + img.offsetY - (float) img.originalHeight / 2.0F,
                    (float) img.originalWidth / 2.0F - img.offsetX - dx, (float) img.originalHeight / 2.0F - img.offsetY - dy,
                    (float) img.packedWidth, (float) img.packedHeight,
                    card.drawScale * Settings.scale, card.drawScale * Settings.scale, card.angle);
        }
    }
}
