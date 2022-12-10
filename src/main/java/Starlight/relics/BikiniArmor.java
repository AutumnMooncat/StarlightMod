package Starlight.relics;

import Starlight.characters.StarlightSisters;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static Starlight.TheStarlightMod.makeID;

public class BikiniArmor extends AbstractEasyRelic {
    public static final String ID = makeID(BikiniArmor.class.getSimpleName());
    private static final int BLOCK = 3;

    HashMap<String, Integer> stats = new HashMap<>();
    private final String BLOCK_STAT = DESCRIPTIONS[2];
    private final String PER_COMBAT_STRING = DESCRIPTIONS[3];

    public BikiniArmor() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    @Override
    public void onPlayerEndTurn() {
        flash();
        addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
        stats.put(BLOCK_STAT, stats.get(BLOCK_STAT) + BLOCK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK + DESCRIPTIONS[1];
    }

    public String getStatsDescription() {
        return BLOCK_STAT + stats.get(BLOCK_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = (float)stats.get(BLOCK_STAT);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(PER_COMBAT_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(BLOCK_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(BLOCK_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(BLOCK_STAT, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        BikiniArmor newRelic = new BikiniArmor();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
