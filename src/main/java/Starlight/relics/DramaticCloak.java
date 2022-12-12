package Starlight.relics;

import Starlight.characters.StarlightSisters;
import Starlight.powers.DramaticPower;
import Starlight.util.Wiz;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static Starlight.TheStarlightMod.makeID;

public class DramaticCloak extends AbstractEasyRelic {
    public static final String ID = makeID(DramaticCloak.class.getSimpleName());

    HashMap<String, Integer> stats = new HashMap<>();
    private final String ATTACKS_MODIFIED_STAT = DESCRIPTIONS[1];
    private final String PER_COMBAT_STRING = DESCRIPTIONS[2];

    public DramaticCloak() {
        super(ID, RelicTier.SHOP, LandingSound.MAGICAL, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        resetStats();
    }

    public void atBattleStart() {
        grayscale = false;
        beginLongPulse();
        Wiz.applyToSelfTop(new DramaticPower(Wiz.adp()));
    }

    @Override
    public void onPlayerEndTurn() {
        stopPulse();
        grayscale = true;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (!grayscale && targetCard.type == AbstractCard.CardType.ATTACK) {
            useCardAction.exhaustCard = true;
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            stats.put(ATTACKS_MODIFIED_STAT, stats.get(ATTACKS_MODIFIED_STAT) + 1);
        }
    }

    //Use a power so it calculates properly
    /*public float atDamageModify(float damage, AbstractCard c) {
        if (grayscale) {
            return damage;
        }
        return damage * 2;
    }*/

    public String getStatsDescription() {
        return ATTACKS_MODIFIED_STAT + stats.get(ATTACKS_MODIFIED_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = (float)stats.get(ATTACKS_MODIFIED_STAT);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(PER_COMBAT_STRING);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(ATTACKS_MODIFIED_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(ATTACKS_MODIFIED_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(ATTACKS_MODIFIED_STAT, jsonArray.get(0).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        DramaticCloak newRelic = new DramaticCloak();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
