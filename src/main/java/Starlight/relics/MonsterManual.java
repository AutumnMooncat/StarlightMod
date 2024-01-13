package Starlight.relics;

import Starlight.characters.StarlightSisters;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static Starlight.TheStarlightMod.makeID;

public class MonsterManual extends AbstractEasyRelic {
    public static final String ID = makeID(MonsterManual.class.getSimpleName());
    private static final int CARDS = 3;

    HashMap<String, Integer> stats = new HashMap<>();
    private final String DRAWN_STAT = DESCRIPTIONS[2];
    private final String PER_COMBAT_STRING = DESCRIPTIONS[3];
    private final String PER_TURN_STRING = DESCRIPTIONS[4];

    public MonsterManual() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        resetStats();
    }

    public void onMonsterDeath(AbstractMonster m) {
        if (m.currentHealth == 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            addToBot(new RelicAboveCreatureAction(m, this));
            addToBot(new DrawCardAction(CARDS, new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard c : DrawCardAction.drawnCards) {
                        if (c.canUpgrade()) {
                            c.upgrade();
                        }
                        stats.put(DRAWN_STAT, stats.get(DRAWN_STAT) + 1);
                    }
                    this.isDone = true;
                }
            }));
        }

    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARDS + DESCRIPTIONS[1];
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
        MonsterManual newRelic = new MonsterManual();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
