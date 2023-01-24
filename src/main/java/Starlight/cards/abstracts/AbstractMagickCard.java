package Starlight.cards.abstracts;

import Starlight.TheStarlightMod;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMagickCard extends AbstractEasyCard {
    private static String tipID = TheStarlightMod.makeID("Magicks");
    private static String[] TEXT = CardCrawlGame.languagePack.getUIString(tipID).TEXT;
    private static List<String> descriptors = new ArrayList<>(Collections.singletonList(TEXT[0]));
    private static List<TooltipInfo> tip = new ArrayList<>(Collections.singletonList(new TooltipInfo(TEXT[1], TEXT[2])));

    public TextureAtlas.AtlasRegion spellbookIcon;
    public boolean checked;

    public AbstractMagickCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }

    public AbstractMagickCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
    }

    @SpireOverride
    public void renderEnergy(SpriteBatch sb) {
        SpireSuper.call(sb);
        if (!checked) {
            spellbookIcon = getSpellbookIcon();
            checked = true;
        }
        if (spellbookIcon != null) {
            renderHelper(sb);
        }
    }

    public TextureAtlas.AtlasRegion getSpellbookIcon() {
        if (hasTag(CustomTags.STARLIGHT_WATER)) {
            return TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookWater.png");
        } else if (hasTag(CustomTags.STARLIGHT_ICE)) {
            return TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookIce.png");
        } else if (hasTag(CustomTags.STARLIGHT_FIRE)) {
            return TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookFire.png");
        } else if (hasTag(CustomTags.STARLIGHT_NATURE)) {
            return TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookNature.png");
        } else if (hasTag(CustomTags.STARLIGHT_DARK)) {
            return TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookDark.png");
        } else if (hasTag(CustomTags.STARLIGHT_LIGHT)) {
            return TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookLight.png");
        } else if (hasTag(CustomTags.STARLIGHT_SPACE)) {
            return TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookSpace.png");
        } else if (hasTag(CustomTags.STARLIGHT_TIME)) {
            return TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookTime.png");
        } else {
            return null;
        }
    }

    private void renderHelper(SpriteBatch sb) {
        float renderScale = 0.9f;
        float dx = -132.0F / renderScale;
        float dy = -192.0F / renderScale;
        float scale = drawScale * Settings.scale * renderScale;
        float w, h, w2, h2;
        w = spellbookIcon.packedWidth;
        w2 = w / 2f;
        h = spellbookIcon.packedHeight;
        h2 = h / 2f;
        sb.draw(spellbookIcon, current_x - w2 - dx, current_y - h2 - dy, w2 + dx, h2 + dy, w, h, scale, scale, angle);
    }

    @Override
    public List<String> getCardDescriptors() {
        return descriptors;
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return tip;
    }
}
