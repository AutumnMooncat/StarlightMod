package Starlight.relics;

import Starlight.actions.ProjectSpecificCardAction;
import Starlight.characters.StarlightSisters;
import Starlight.powers.SpellPower;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class PureFeather extends AbstractEasyRelic {
    public static final String ID = makeID(PureFeather.class.getSimpleName());

    public PureFeather() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    public void atBattleStart() {
        grayscale = false;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && !grayscale && !ProjectedCardManager.ProjectedActionField.projectedField.get(action)) {
            grayscale = true;
            this.flash();
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            addToTop(new ProjectSpecificCardAction(tmp));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }
}
