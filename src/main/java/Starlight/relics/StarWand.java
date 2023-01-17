package Starlight.relics;

import Starlight.cards.token.Starburst;
import Starlight.characters.StarlightSisters;
import Starlight.util.Wiz;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;

import static Starlight.TheStarlightMod.makeID;

public class StarWand extends AbstractEasyRelic {
    public static final String ID = makeID(StarWand.class.getSimpleName());

    HashMap<String, Integer> stats = new HashMap<>();
    private final String ATTACKS_STAT = DESCRIPTIONS[1];
    private final String SKILLS_STAT = DESCRIPTIONS[2];

    public StarWand() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        resetStats();
    }

    public void atBattleStartPreDraw() {
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new MakeTempCardInHandAction(new Starburst(), 3, false));
    }

    /*@Override
    public void atTurnStart() {
        flash();
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
    }*/

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard instanceof Starburst) {
            stats.put(SKILLS_STAT, stats.get(SKILLS_STAT) + targetCard.block);
        }
    }

    public void updateDamage(int damage) {
        stats.put(ATTACKS_STAT, stats.get(ATTACKS_STAT) + damage);
    }

    @Override //Should replace default relic. Big thanks papa kio
    public void obtain() {
        //Grab the player
        AbstractPlayer p = AbstractDungeon.player;
        //If we have the starter relic...
        if (p.hasRelic(MagicWand.ID)) {
            //Grab its data for relic stats if you want to carry the stats over to the boss relic
            MagicWand mw = (MagicWand) p.getRelic(MagicWand.ID);
            stats.put(ATTACKS_STAT, mw.getAttacks());
            stats.put(SKILLS_STAT, mw.getSkills());
            //Find it...
            for (int i = 0; i < p.relics.size(); ++i) {
                if (p.relics.get(i).relicId.equals(MagicWand.ID)) {
                    //Replace it
                    instantObtain(p, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    //Only spawn if we have the starter relic
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(MagicWand.ID);
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
        StarWand newRelic = new StarWand();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
