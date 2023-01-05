package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractAbilityCard;
import Starlight.cards.bookOfTime.Haste;
import Starlight.powers.abilities.FrameSkipPower;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BookOfTime extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfTime")).TEXT;
    private static final int AMOUNT = 1;

    public BookOfTime(boolean prim) {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookTime.png"), TEXT[0], TEXT[1], new AbstractAbilityCard(new FrameSkipPower(null, AMOUNT, prim)) {});
    }

    @Override
    protected void playSFX() {
        CardCrawlGame.sound.play("POWER_TIME_WARP");
    }

    @Override
    public String starterCardID() {
        return Haste.ID;
    }

    @Override
    public boolean allowCardInPool(AbstractCard card) {
        return card.hasTag(CustomTags.STARLIGHT_TIME);
    }

    @Override
    public AbstractPower getAbility(boolean primrose) {
        return new FrameSkipPower(Wiz.adp(), AMOUNT, primrose);
    }
}
