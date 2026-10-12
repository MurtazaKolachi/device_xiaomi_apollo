/*
 * Copyright (C) 2026 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.settings.torch;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import org.lineageos.settings.utils.FileUtils;

import java.io.File;

public final class TorchUtils {

    public static final String FLASHLIGHT_PATH = "/sys/class/leds/flashlight/brightness";
    public static final String MAX_BRIGHTNESS_PATH = "/sys/class/leds/flashlight/max_brightness";

    public static final String PREF_TORCH_LEVEL = "torch_strength_level";
    public static final int DEFAULT_TORCH_LEVEL = 750;
    public static final int MIN_TORCH_LEVEL = 50;

    private TorchUtils() {
    }

    public static boolean isTorchSupported() {
        return FileUtils.fileExists(FLASHLIGHT_PATH);
    }

    public static int getMaxBrightness() {
        String max = FileUtils.readOneLine(MAX_BRIGHTNESS_PATH);
        if (max != null && !max.trim().isEmpty()) {
            try {
                return Integer.parseInt(max.trim());
            } catch (NumberFormatException ignored) {
            }
        }
        return DEFAULT_TORCH_LEVEL;
    }

    public static int getSavedLevel(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(PREF_TORCH_LEVEL, DEFAULT_TORCH_LEVEL);
    }

    public static void setSavedLevel(Context context, int level) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt(PREF_TORCH_LEVEL, level).apply();
    }

    public static int getCurrentBrightness() {
        String val = FileUtils.readOneLine(FLASHLIGHT_PATH);
        if (val != null && !val.trim().isEmpty()) {
            try {
                return Integer.parseInt(val.trim());
            } catch (NumberFormatException ignored) {
            }
        }
        return 0;
    }

    public static boolean isTorchOn() {
        return getCurrentBrightness() > 0;
    }

    public static void setTorchBrightness(int level) {
        FileUtils.writeLine(FLASHLIGHT_PATH, String.valueOf(level));
    }

    public static void toggleTorch(Context context) {
        if (isTorchOn()) {
            setTorchBrightness(0);
        } else {
            int level = getSavedLevel(context);
            if (level <= 0) {
                level = DEFAULT_TORCH_LEVEL;
            }
            setTorchBrightness(level);
        }
    }
}
