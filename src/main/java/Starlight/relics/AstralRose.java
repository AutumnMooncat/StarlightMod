package Starlight.relics;

import Starlight.actions.ProjectTopCardAction;
import Starlight.characters.StarlightSisters;
import Starlight.relics.interfaces.PostEndTurnPowerRelic;
import Starlight.util.Wiz;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static Starlight.TheStarlightMod.makeID;

public class AstralRose extends AbstractEasyRelic implements PostEndTurnPowerRelic {
    public static final String ID = makeID(AstralRose.class.getSimpleName());

    HashMap<String, Integer> stats = new HashMap<>();
    private final String CARDS_STAT = DESCRIPTIONS[1];
    private final String ENERGY_SAVED_STAT = DESCRIPTIONS[2];
    private final String PER_COMBAT_STRING = DESCRIPTIONS[3];
    private final String PER_TURN_STRING = DESCRIPTIONS[4];

    public AstralRose() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        resetStats();
    }

    public String getStatsDescription() {
        return CARDS_STAT + stats.get(CARDS_STAT) + ENERGY_SAVED_STAT + stats.get(ENERGY_SAVED_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = (float)stats.get(ENERGY_SAVED_STAT);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(PER_TURN_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalTurns, 1)));
        builder.append(PER_COMBAT_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(CARDS_STAT, 0);
        stats.put(ENERGY_SAVED_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(CARDS_STAT));
        statsToSave.add(stats.get(ENERGY_SAVED_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(CARDS_STAT, jsonArray.get(0).getAsInt());
            stats.put(ENERGY_SAVED_STAT, jsonArray.get(1).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        AstralRose newRelic = new AstralRose();
        newRelic.stats = this.stats;
        return newRelic;
    }

    @Override
    public void postEndTurn() {
        if (Wiz.adp().drawPile.group.stream().anyMatch(c -> c.cost != -2)) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ProjectTopCardAction(1, true, new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard c : ProjectTopCardAction.projectedCards) {
                        stats.put(CARDS_STAT, stats.get(CARDS_STAT) + 1);
                        stats.put(ENERGY_SAVED_STAT, stats.get(ENERGY_SAVED_STAT) + c.costForTurn);
                    }
                    this.isDone = true;
                }
            }));
        }
    }
}
