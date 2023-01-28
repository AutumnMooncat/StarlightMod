package Starlight.relics;

import Starlight.actions.ProjectSpecificCardAction;
import Starlight.characters.StarlightSisters;
import Starlight.ui.ProjectedCardManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;

import static Starlight.TheStarlightMod.makeID;

public class PureFeather extends AbstractEasyRelic {
    public static final String ID = makeID(PureFeather.class.getSimpleName());

    HashMap<String, Integer> stats = new HashMap<>();
    private final String ATTACKS_STAT = DESCRIPTIONS[1];
    private final String SKILLS_STAT = DESCRIPTIONS[2];
    private final String POWERS_STAT = DESCRIPTIONS[3];

    public PureFeather() {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        resetStats();
    }

    public void atBattleStart() {
        grayscale = false;
        beginLongPulse();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && !grayscale && !ProjectedCardManager.ProjectedActionField.projectedField.get(action)) {
            grayscale = true;
            stopPulse();
            this.flash();
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            addToTop(new ProjectSpecificCardAction(tmp));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            if (card.type == AbstractCard.CardType.ATTACK) {
                stats.put(ATTACKS_STAT, stats.get(ATTACKS_STAT) + 1);
            } else if (card.type == AbstractCard.CardType.SKILL) {
                stats.put(SKILLS_STAT, stats.get(SKILLS_STAT) + 1);
            } else if (card.type == AbstractCard.CardType.POWER) {
                stats.put(POWERS_STAT, stats.get(POWERS_STAT) + 1);
            }
        }
    }

    public String getStatsDescription() {
        return ATTACKS_STAT + stats.get(ATTACKS_STAT) + SKILLS_STAT + stats.get(SKILLS_STAT) + POWERS_STAT + stats.get(POWERS_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        return getStatsDescription();
    }

    public void resetStats() {
        stats.put(ATTACKS_STAT, 0);
        stats.put(SKILLS_STAT, 0);
        stats.put(POWERS_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(ATTACKS_STAT));
        statsToSave.add(stats.get(SKILLS_STAT));
        statsToSave.add(stats.get(POWERS_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(ATTACKS_STAT, jsonArray.get(0).getAsInt());
            stats.put(SKILLS_STAT, jsonArray.get(1).getAsInt());
            stats.put(POWERS_STAT, jsonArray.get(2).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        PureFeather newRelic = new PureFeather();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
