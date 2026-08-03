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

package org.lineageos.settings;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class TorchTileService extends TileService {

    private static final String FLASHLIGHT = "/sys/class/leds/flashlight/brightness";

    private boolean isTorchOn() {
        try {
            File f = new File(FLASHLIGHT);
            if (f.exists()) {
                Scanner scanner = new Scanner(f);
                if (scanner.hasNextInt()) {
                    return scanner.nextInt() > 0;
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return false;
    }

    private void setTorchBrightness(String value) {
        try {
            FileWriter writer = new FileWriter(FLASHLIGHT);
            writer.write(value);
            writer.close();
        } catch (Exception e) {
            // Ignore
        }
    }

    private void toggleTorch() {
        if (isTorchOn()) {
            setTorchBrightness("0");
        } else {
            setTorchBrightness("750");
        }
    }

    private void updateTileView() {
        Tile tile = getQsTile();
        if (tile == null) return;
        boolean on = isTorchOn();
        tile.setIcon(android.graphics.drawable.Icon.createWithResource(this, R.drawable.ic_qs_torch));
        tile.setState(on ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.setSubtitle(on ? "On" : "Off");
        tile.updateTile();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTileView();
    }

    @Override
    public void onClick() {
        super.onClick();
        toggleTorch();
        updateTileView();
    }
}
