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

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.lineageos.settings.R;

public class TorchSettingsActivity extends Activity {

    private SeekBar mSlider;
    private TextView mValueText;
    private Button mToggleButton;
    private Vibrator mVibrator;

    private int mMaxBrightness;
    private int mLastNotifiedStep = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.torch_dialog_layout);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mMaxBrightness = TorchUtils.getMaxBrightness();

        mValueText = findViewById(R.id.torch_strength_value);
        mSlider = findViewById(R.id.torch_strength_slider);
        mToggleButton = findViewById(R.id.torch_toggle_button);

        mSlider.setMax(mMaxBrightness - TorchUtils.MIN_TORCH_LEVEL);

        int currentLevel = TorchUtils.getCurrentBrightness();
        if (currentLevel <= 0) {
            currentLevel = TorchUtils.getSavedLevel(this);
        }
        if (currentLevel < TorchUtils.MIN_TORCH_LEVEL) {
            currentLevel = TorchUtils.MIN_TORCH_LEVEL;
        }

        mSlider.setProgress(currentLevel - TorchUtils.MIN_TORCH_LEVEL);
        updateValueDisplay(currentLevel);
        updateToggleButton();

        mSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int level = progress + TorchUtils.MIN_TORCH_LEVEL;
                updateValueDisplay(level);

                if (fromUser) {
                    TorchUtils.setSavedLevel(TorchSettingsActivity.this, level);
                    if (TorchUtils.isTorchOn()) {
                        TorchUtils.setTorchBrightness(level);
                    }
                    performHapticFeedback(level);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mToggleButton.setOnClickListener(v -> {
            TorchUtils.toggleTorch(this);
            updateToggleButton();
            int progress = mSlider.getProgress() + TorchUtils.MIN_TORCH_LEVEL;
            updateValueDisplay(progress);
        });
    }

    private void updateValueDisplay(int level) {
        int percent = Math.round(((float) level / (float) mMaxBrightness) * 100.0f);
        mValueText.setText(percent + "%");
    }

    private void updateToggleButton() {
        boolean isOn = TorchUtils.isTorchOn();
        mToggleButton.setText(isOn ? R.string.torch_state_turn_off : R.string.torch_state_turn_on);
    }

    private void performHapticFeedback(int level) {
        if (mVibrator == null || !mVibrator.hasVibrator()) {
            return;
        }
        int step = level / 50;
        if (step != mLastNotifiedStep) {
            mLastNotifiedStep = step;
            try {
                mVibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK));
            } catch (Exception e) {
                // Fallback for devices without EFFECT_TICK
                mVibrator.vibrate(10);
            }
        }
    }
}
