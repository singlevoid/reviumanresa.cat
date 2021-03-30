package com.singlevoid.caterina.ui.main.settings.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.singlevoid.caterina.R;
import com.singlevoid.caterina.data.AppValues;

class SettingsThemeView extends ConstraintLayout {

    private View root;
    private ImageButton arrow;
    private RadioGroup options;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SettingsThemeView(@NonNull Context context) {
        super(context);
        initView();
    }

    public SettingsThemeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SettingsThemeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public SettingsThemeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        preferences =
                getContext().getSharedPreferences(getContext().getString(R.string.PREFERENCES_KEY),
                Context.MODE_PRIVATE);

        root = inflate(getContext(), R.layout.item_settings_theme, this);
        options = root.findViewById(R.id.item_settings_theme_radio_group);
        View button = root.findViewById(R.id.item_settings_card_view);
        arrow = root.findViewById(R.id.item_settings_theme_arrow);
        arrow.setOnClickListener(this::titleClicked);
        button.setOnClickListener(this::titleClicked);
        options.setOnCheckedChangeListener(this::itemSelected);
        int current = preferences.getInt(getContext().getString(R.string.PREFERENCE_THEME),
                AppValues.THEME_DEFAULT);
        // ((RadioButton)options.getChildAt(current)).setChecked(true);

    }


    private void itemSelected(RadioGroup radioGroup, int i) {
        editor = preferences.edit();
        Log.d("INTEGER", "" + i);
        if(i == R.id.item_settings_theme_radio_default){
           editor.putInt(getContext().getString(R.string.PREFERENCE_THEME), AppValues.THEME_DEFAULT);
        }
        else if(i == R.id.item_settings_theme_radio_light){
            editor.putInt(getContext().getString(R.string.PREFERENCE_THEME), AppValues.THEME_LIGHT);
        }
        else if(i == R.id.item_settings_theme_radio_dark){
            editor.putInt(getContext().getString(R.string.PREFERENCE_THEME), AppValues.THEME_DARK);
        }

        editor.apply();
    }

    private void titleClicked(View view) {
        if(options.getVisibility() == View.GONE) {
            options.setVisibility(View.VISIBLE);
            arrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        }else{
            options.setVisibility(View.GONE);
            arrow.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);
        }
    }


}
