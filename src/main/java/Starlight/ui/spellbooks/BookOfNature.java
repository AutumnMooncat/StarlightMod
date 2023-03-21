package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.bookOfNature.VineWrap;
import Starlight.powers.abilities.ThornyVinesPower;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BookOfNature extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfNature")).TEXT;
    private static final int AMOUNT = 5;

    public BookOfNature(boolean prim) {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookNature.png"), TEXT[0], TEXT[1]);
    }

    @Override
    protected void playSFX() {
        CardCrawlGame.sound.play("ATTACK_FAST");
    }

    @Override
    public String starterCardID() {
        return VineWrap.ID;
    }

    @Override
    public boolean allowCardInPool(AbstractCard card) {
        return card.hasTag(CustomTags.STARLIGHT_NATURE);
    }

    @Override
    public AbstractPower getAbility(boolean primrose) {
        return new ThornyVinesPower(Wiz.adp(), AMOUNT, primrose);
    }
}
