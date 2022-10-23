package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.bookOfIce.FrostRay;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BookOfIce extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfIce")).TEXT;
    public BookOfIce() {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID+"Resources/images/books/BookIce.png"), TEXT[0], TEXT[1]);
    }

    @Override
    protected void playSFX() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL");
    }

    @Override
    public String starterCardID() {
        return FrostRay.ID;
    }

    @Override
    public boolean allowCardInPool(AbstractCard card) {
        return card.hasTag(CustomTags.STARLIGHT_ICE);
    }
}
