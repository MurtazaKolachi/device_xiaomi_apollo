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

import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import org.lineageos.settings.torch.TorchUtils;

public class TorchTileService extends TileService {

    private void updateTileView() {
        Tile tile = getQsTile();
        if (tile == null) return;
        boolean on = TorchUtils.isTorchOn();
        tile.setIcon(Icon.createWithResource(this, R.drawable.ic_qs_torch));
        tile.setState(on ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.setSubtitle(on ? getString(R.string.torch_state_on) : getString(R.string.torch_state_off));
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
        TorchUtils.toggleTorch(this);
        updateTileView();
    }
}
