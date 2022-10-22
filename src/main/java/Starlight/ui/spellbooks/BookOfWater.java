package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.bookOfWater.WaterBall;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BookOfWater extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfWater")).TEXT;
    public BookOfWater() {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID+"Resources/images/books/BookWater.png"), TEXT[0], TEXT[1]);
    }

    @Override
    protected void playSFX() {
        CardCrawlGame.sound.play("POTION_2");
    }

    @Override
    public String starterCardID() {
        return WaterBall.ID;
    }

    @Override
    public boolean allowCardInPool(AbstractCard card) {
        return card.hasTag(CustomTags.STARLIGHT_WATER);
    }
}
