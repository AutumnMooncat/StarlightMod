package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractAbilityCard;
import Starlight.cards.bookOfDark.ShadowArrow;
import Starlight.cards.bookOfWater.WaterBall;
import Starlight.powers.abilities.BlackMagicPower;
import Starlight.powers.abilities.SoakPower;
import Starlight.util.CustomTags;
import Starlight.util.TexLoader;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BookOfDark extends ClickableSpellbook {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("BookOfDark")).TEXT;
    private static final int AMOUNT = 1;

    public BookOfDark(boolean prim) {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/books/BookDark.png"), TEXT[0], TEXT[1], new AbstractAbilityCard(new BlackMagicPower(null, prim)) {});
    }

    @Override
    protected void playSFX() {
        CardCrawlGame.sound.play("ATTACK_POISON");
    }

    @Override
    public String starterCardID() {
        return ShadowArrow.ID;
    }

    @Override
    public boolean allowCardInPool(AbstractCard card) {
        return card.hasTag(CustomTags.STARLIGHT_DARK);
    }

    @Override
    public AbstractPower getAbility(boolean primrose) {
        return new BlackMagicPower(Wiz.adp(), primrose);
    }
}
