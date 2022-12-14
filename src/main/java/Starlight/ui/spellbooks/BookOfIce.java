package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractAbilityCard;
import Starlight.cards.bookOfIce.Reflection;
import Starlight.powers.abilities.SnowCloakPower;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BookOfIce extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfIce")).TEXT;
    private static final int AMOUNT = 1;

    public BookOfIce(boolean prim) {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookIce.png"), TEXT[0], TEXT[1], new AbstractAbilityCard(new SnowCloakPower(null, AMOUNT, prim)) {});
    }

    @Override
    protected void playSFX() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL");
    }

    @Override
    public String starterCardID() {
        return Reflection.ID;
    }

    @Override
    public boolean allowCardInPool(AbstractCard card) {
        return card.hasTag(CustomTags.STARLIGHT_ICE);
    }

    @Override
    public AbstractPower getAbility(boolean primrose) {
        return new SnowCloakPower(Wiz.adp(), AMOUNT, primrose);
    }
}
