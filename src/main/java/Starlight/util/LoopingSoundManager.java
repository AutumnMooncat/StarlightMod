package Starlight.util;

import basemod.Pair;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class LoopingSoundManager {
    public static final ArrayList<Pair<String, Long>> loopedSounds = new ArrayList<>();

    public static Pair<String, Long> addLoopedSound(String key) {
        Pair<String, Long> p = new Pair<>(key, CardCrawlGame.sound.playAndLoop(key));
        loopedSounds.add(p);
        return p;
    }

    public static void stopLoopedSound(Pair<String, Long> pair) {
        CardCrawlGame.sound.stop(pair.getKey(), pair.getValue());
    }

    public static void stopAllLoopedSounds() {
        for (Pair<String, Long> p : loopedSounds) {
            stopLoopedSound(p);
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "resetPlayer")
    @SpirePatch2(clz = CardCrawlGame.class, method = "startOver")
    public static class StopLoopingPlz {
        @SpirePostfixPatch
        public static void plz() {
            stopAllLoopedSounds();
        }
    }
}
