package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractAbilityCard;
import Starlight.cards.abstracts.AbstractPowerCard;
import Starlight.cards.bookOfWater.WaterBall;
import Starlight.powers.abilities.FiredUpPower;
import Starlight.powers.abilities.SoakPower;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BookOfWater extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfWater")).TEXT;
    private static final int AMOUNT = 1;

    public BookOfWater(boolean prim) {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookWater.png"), TEXT[0], TEXT[1], new AbstractAbilityCard(new SoakPower(null, AMOUNT, prim)) {});
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

    @Override
    public AbstractPower getAbility(boolean primrose) {
        return new SoakPower(Wiz.adp(), AMOUNT, primrose);
    }
}
