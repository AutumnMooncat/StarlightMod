package Starlight.relics;

import Starlight.characters.StarlightSisters;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static Starlight.TheStarlightMod.makeID;

public class CursedBelt extends AbstractEasyRelic {
    public static final String ID = makeID(CursedBelt.class.getSimpleName());

    HashMap<String, Integer> stats = new HashMap<>();
    private final String DRAWN_STAT = DESCRIPTIONS[1];
    private final String PER_COMBAT_STRING = DESCRIPTIONS[2];
    private final String PER_TURN_STRING = DESCRIPTIONS[3];

    public CursedBelt() {
        super(ID, RelicTier.SHOP, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    @Override
    public void atPreBattle() {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToTop(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true, false));
    }

    public void onExhaust(AbstractCard card) {
        flash();
        addToBot(new DrawCardAction(1, new AbstractGameAction() {
            @Override
            public void update() {
                stats.put(DRAWN_STAT, stats.get(DRAWN_STAT) + DrawCardAction.drawnCards.size());
                this.isDone = true;
            }
        }));
    }

    public String getStatsDescription() {
        return DRAWN_STAT + stats.get(DRAWN_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = (float)stats.get(DRAWN_STAT);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(PER_TURN_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalTurns, 1)));
        builder.append(PER_COMBAT_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(DRAWN_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(DRAWN_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(DRAWN_STAT, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        CursedBelt newRelic = new CursedBelt();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
