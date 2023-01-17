package Starlight.relics;

import Starlight.cards.token.Starburst;
import Starlight.characters.StarlightSisters;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static Starlight.TheStarlightMod.makeID;

public class MagicWand extends AbstractEasyRelic {
    public static final String ID = makeID(MagicWand.class.getSimpleName());

    HashMap<String, Integer> stats = new HashMap<>();
    private final String DAMAGE_STAT = DESCRIPTIONS[1];
    private final String BLOCK_STAT = DESCRIPTIONS[2];
    private final String DPC = DESCRIPTIONS[3];
    private final String BPC = DESCRIPTIONS[4];


    public MagicWand() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        resetStats();
    }

    public void atBattleStartPreDraw() {
        //flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new MakeTempCardInHandAction(new Starburst(), 1, false));
        /*addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int draw = 0;
                if (!Wiz.adp().drawPile.getCardsOfType(AbstractCard.CardType.SKILL).isEmpty()) {
                    AbstractCard c = Wiz.adp().drawPile.getCardsOfType(AbstractCard.CardType.SKILL).getRandomCard(true);
                    Wiz.adp().drawPile.group.remove(c);
                    Wiz.adp().drawPile.group.add(c);
                    draw++;
                }
                if (!Wiz.adp().drawPile.getCardsOfType(AbstractCard.CardType.ATTACK).isEmpty()) {
                    AbstractCard c = Wiz.adp().drawPile.getCardsOfType(AbstractCard.CardType.ATTACK).getRandomCard(true);
                    Wiz.adp().drawPile.group.remove(c);
                    Wiz.adp().drawPile.group.add(c);
                    draw++;
                }
                Wiz.att(new DrawCardAction(draw, new AbstractGameAction() {
                    @Override
                    public void update() {
                        for (AbstractCard c : DrawCardAction.drawnCards) {
                            if (c.type == AbstractCard.CardType.ATTACK) {
                                stats.put(ATTACKS_STAT, stats.get(ATTACKS_STAT) + 1);
                            } else if (c.type == AbstractCard.CardType.SKILL) {
                                stats.put(SKILLS_STAT, stats.get(SKILLS_STAT) + 1);
                            }
                        }
                        this.isDone = true;
                    }
                }));
                this.isDone = true;
            }
        });*/
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard instanceof Starburst) {
            stats.put(BLOCK_STAT, stats.get(BLOCK_STAT) + targetCard.block);
        }
    }

    public void updateDamage(int damage) {
        stats.put(DAMAGE_STAT, stats.get(DAMAGE_STAT) + damage);
    }

    public int getAttacks() {
        return stats.get(DAMAGE_STAT);
    }

    public int getSkills() {
        return stats.get(BLOCK_STAT);
    }

    public String getStatsDescription() {
        return DAMAGE_STAT + stats.get(DAMAGE_STAT) + BLOCK_STAT + stats.get(BLOCK_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        // You would just return getStatsDescription() if you don't want to display per-combat and per-turn stats
        StringBuilder builder = new StringBuilder();
        builder.append(getStatsDescription());
        float stat = stats.get(DAMAGE_STAT);
        // Relic Stats truncates these extended stats to 3 decimal places, so we do the same
        DecimalFormat perTurnFormat = new DecimalFormat("#.###");
        builder.append(DPC);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        stat = stats.get(BLOCK_STAT);
        builder.append(BPC);
        builder.append(perTurnFormat.format(stat / Math.max(totalCombats, 1)));
        return builder.toString();
    }

    public void resetStats() {
        stats.put(DAMAGE_STAT, 0);
        stats.put(BLOCK_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(DAMAGE_STAT));
        statsToSave.add(stats.get(BLOCK_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(DAMAGE_STAT, jsonArray.get(0).getAsInt());
            stats.put(BLOCK_STAT, jsonArray.get(1).getAsInt());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        MagicWand newRelic = new MagicWand();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
