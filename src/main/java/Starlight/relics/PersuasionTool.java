package Starlight.relics;

import Starlight.cardmods.ForcedMagicMod;
import Starlight.characters.StarlightSisters;
import Starlight.util.FormatHelper;
import Starlight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.HashMap;

import static Starlight.TheStarlightMod.makeID;

public class PersuasionTool extends AbstractEasyRelic {
    public static final String ID = makeID(PersuasionTool.class.getSimpleName());
    private static final int CARDS = 2;

    private boolean cardsSelected = true;
    private int selection = 2;

    HashMap<String, String> stats = new HashMap<>();
    private final String ATTACKS_PICKED_STAT = DESCRIPTIONS[3];

    public PersuasionTool() {
        super(ID, RelicTier.RARE, LandingSound.HEAVY, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        resetStats();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + CARDS + DESCRIPTIONS[1];
    }

    public void onEquip() {
        this.cardsSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.type == AbstractCard.CardType.ATTACK && !Wiz.isMagic(c))
                tmp.addToTop(c);
        }
        if (tmp.group.isEmpty()) {
            this.cardsSelected = true;
        } else {
            selection = Math.min(2, tmp.size());
            AbstractDungeon.gridSelectScreen.open(tmp, selection, DESCRIPTIONS[2], false, false, false, false);
        }
    }

    public void update() {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == selection) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            CardModifierManager.addModifier(c, new ForcedMagicMod());
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH/3f, Settings.HEIGHT/2f));
            String name = c.name;
            if (c.rarity == AbstractCard.CardRarity.UNCOMMON) {
                name = FormatHelper.prefixWords(name, "#b");
            } else if (c.rarity == AbstractCard.CardRarity.RARE) {
                name = FormatHelper.prefixWords(name, "#y");
            }
            stats.put(ATTACKS_PICKED_STAT, stats.get(ATTACKS_PICKED_STAT) + " NL " + name);
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 2) {
                c = AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                CardModifierManager.addModifier(c, new ForcedMagicMod());
                AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH*2/3f, Settings.HEIGHT/2f));
                name = c.name;
                if (c.rarity == AbstractCard.CardRarity.UNCOMMON) {
                    name = FormatHelper.prefixWords(name, "#b");
                } else if (c.rarity == AbstractCard.CardRarity.RARE) {
                    name = FormatHelper.prefixWords(name, "#y");
                }
                stats.put(ATTACKS_PICKED_STAT, stats.get(ATTACKS_PICKED_STAT) + " NL " + name);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.cardsSelected = true;
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    @Override
    public boolean canSpawn() {
        return Wiz.adp().masterDeck.group.stream().filter(c -> c.type == AbstractCard.CardType.ATTACK && !Wiz.isMagic(c)).count() >= 2;
    }

    public String getStatsDescription() {
        return ATTACKS_PICKED_STAT + stats.get(ATTACKS_PICKED_STAT);
    }

    public String getExtendedStatsDescription(int totalCombats, int totalTurns) {
        return getStatsDescription();
    }

    public void resetStats() {
        stats.put(ATTACKS_PICKED_STAT, "");
    }

    public JsonElement onSaveStats() {
        // An array makes more sense if you want to store more than one stat
        Gson gson = new Gson();
        ArrayList<String> statsToSave = new ArrayList<>();
        statsToSave.add(stats.get(ATTACKS_PICKED_STAT));
        return gson.toJsonTree(statsToSave);
    }

    public void onLoadStats(JsonElement jsonElement) {
        if (jsonElement != null) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            stats.put(ATTACKS_PICKED_STAT, jsonArray.get(0).getAsString());
        } else {
            resetStats();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        // Relic Stats will always query the stats from the instance passed to BaseMod.addRelic()
        // Therefore, we make sure all copies share the same stats by copying the HashMap.
        PersuasionTool newRelic = new PersuasionTool();
        newRelic.stats = this.stats;
        return newRelic;
    }
}
