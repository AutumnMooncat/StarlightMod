package Starlight.relics;

import Starlight.characters.StarlightSisters;
import Starlight.util.Wiz;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;

import static Starlight.TheStarlightMod.makeID;

public class MagicWand extends AbstractEasyRelic {
    public static final String ID = makeID(MagicWand.class.getSimpleName());

    HashMap<String, Integer> stats = new HashMap<>();
    private final String ATTACKS_STAT = DESCRIPTIONS[1];
    private final String SKILLS_STAT = DESCRIPTIONS[2];

    public MagicWand() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        resetStats();
    }

    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new AbstractGameAction() {
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
        });
    }

    public int getAttacks() {
        return stats.get(ATTACKS_STAT);
    }

    public int getSkills() {
        return stats.get(SKILLS_STAT);
    }

    public String getStatsDescription() {
        return ATTACKS_STAT + stats.get(ATTACKS_STAT) + SKILLS_STAT + stats.get(SKILLS_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        return getStatsDescription();
    }

    public void resetStats() {
        stats.put(ATTACKS_STAT, 0);
        stats.put(SKILLS_STAT, 0);
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<Integer> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(ATTACKS_STAT));
        statsToSave.add(stats.get(SKILLS_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(ATTACKS_STAT, jsonArray.get(0).getAsInt());
            stats.put(SKILLS_STAT, jsonArray.get(1).getAsInt());
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
