package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractAbilityCard;
import Starlight.cards.bookOfSpace.Eclipse;
import Starlight.powers.abilities.EquinoxPower;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BookOfSpace extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfSpace")).TEXT;
    private static final int AMOUNT = 2;

    public BookOfSpace(boolean prim) {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookSpace.png"), TEXT[0], TEXT[1]);
    }

    @Override
    protected void playSFX() {
        CardCrawlGame.sound.play("APPEAR");
    }

    @Override
    public String starterCardID() {
        return Eclipse.ID;
    }

    @Override
    public boolean allowCardInPool(AbstractCard card) {
        return card.hasTag(CustomTags.STARLIGHT_SPACE);
    }

    @Override
    public AbstractPower getAbility(boolean primrose) {
        return new EquinoxPower(Wiz.adp(), AMOUNT, primrose);
    }
}
