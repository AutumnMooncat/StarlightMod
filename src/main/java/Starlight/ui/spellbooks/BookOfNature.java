package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.Float;
import Starlight.cards.Strike;
import Starlight.cards.bookOfNature.VineWrap;
import Starlight.util.TexLoader;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BookOfNature extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfNature")).TEXT;
    public BookOfNature() {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID+"Resources/images/books/BookNature.png"), TEXT[0], TEXT[1]);
    }

    @Override
    protected void playSFX() {
        CardCrawlGame.sound.play("ATTACK_FAST");
    }

    @Override
    public String starterCardID() {
        return VineWrap.ID;
    }
}
