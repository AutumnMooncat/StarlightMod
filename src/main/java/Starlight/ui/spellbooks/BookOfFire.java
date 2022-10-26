package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractAbilityCard;
import Starlight.cards.bookOfFire.FireBolt;
import Starlight.powers.abilities.FiredUpPower;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BookOfFire extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfFire")).TEXT;
    public static final int AMOUNT = 2;

    public BookOfFire(boolean prim) {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookFire.png"), TEXT[0], TEXT[1], new AbstractAbilityCard(new FiredUpPower(null, AMOUNT, prim)) {});
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

    @Override
    public AbstractPower getAbility(boolean primrose) {
        return new FiredUpPower(Wiz.adp(), AMOUNT, primrose);
    }
}
