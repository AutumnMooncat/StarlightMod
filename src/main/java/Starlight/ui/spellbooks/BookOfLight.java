package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractAbilityCard;
import Starlight.cards.bookOfDark.ShadowArrow;
import Starlight.cards.bookOfLight.Geas;
import Starlight.powers.abilities.BlackMagicPower;
import Starlight.powers.abilities.CovenantPower;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BookOfLight extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfLight")).TEXT;
    private static final int AMOUNT = 1;

    public BookOfLight(boolean prim) {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookLight.png"), TEXT[0], TEXT[1], new AbstractAbilityCard(new CovenantPower(null, AMOUNT, prim)) {});
    }

    @Override
    protected void playSFX() {
        CardCrawlGame.sound.play("HEAL_1");
    }

    @Override
    public String starterCardID() {
        return Geas.ID;
    }

    @Override
    public boolean allowCardInPool(AbstractCard card) {
        return card.hasTag(CustomTags.STARLIGHT_LIGHT);
    }

    @Override
    public AbstractPower getAbility(boolean primrose) {
        return new CovenantPower(Wiz.adp(), AMOUNT, primrose);
    }
}
