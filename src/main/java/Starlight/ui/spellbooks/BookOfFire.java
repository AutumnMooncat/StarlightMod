package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.Fire;
import Starlight.cards.bookOfFire.FireBolt;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class BookOfFire extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfFire")).TEXT;
    public BookOfFire() {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID+"Resources/images/books/BookFire.png"), TEXT[0], TEXT[1]);
    }

    @Override
    protected void playSFX() {
        CardCrawlGame.sound.play("ATTACK_FIRE");
    }

    @Override
    public String starterCardID() {
        return FireBolt.ID;
    }

    @Override
    public boolean allowCardInPool(AbstractCard card) {
        return card.hasTag(CustomTags.STARLIGHT_FIRE);
    }
}
