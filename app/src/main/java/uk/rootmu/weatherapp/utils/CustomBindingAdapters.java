package uk.rootmu.weatherapp.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;
import java.util.function.Function;

public class CustomBindingAdapters {

    @BindingAdapter("temperatureValue")
    public static void setTemperatureValue(TextInputEditText textView, float temperature) {
        String formattedTemperature = formatTemperature(temperature);
        textView.setText(formattedTemperature);
    }

    @BindingAdapter("temperatureValue")
    public static void setTemperatureValue(TextView textView, double temperature) {
        String formattedTemperature = formatTemperature((float)temperature);
        textView.setText(formattedTemperature);
    }

    @InverseBindingAdapter(attribute = "temperatureValue")
    public static float getTemperatureValue(TextInputEditText textView) {
        String input = textView.getText().toString().trim();
        if (input.isEmpty()) {
            return 0.0f;
        } else {
            // Remove the temperature suffix and parse the float value
            try {
                return Float.parseFloat(input.replace("°C", ""));
            } catch (NumberFormatException e) {
                return 0.0f;
            }
        }
    }

    @BindingAdapter("temperatureValueAttrChanged")
    public static void setListener(TextInputEditText textView, final InverseBindingListener listener) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //not used
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //not used
            }

            @Override
            public void afterTextChanged(Editable editable) {
                listener.onChange();
            }
        });
    }

    private static String formatTemperature(float temperature) {
        return String.format(Locale.getDefault(), "%.1f°C", temperature);
    }

    @BindingAdapter("selectedItem")
    public static void setSelectedItem(BottomNavigationView view, int selectedItem) {
        view.setSelectedItemId(selectedItem);
    }

    @InverseBindingAdapter(attribute = "selectedItem", event = "selectedItemAttrChanged")
    public static int getSelectedItem(BottomNavigationView view) {
        return view.getSelectedItemId();
    }

    @BindingAdapter("selectedItemAttrChanged")
    public static void setItemSelectedListener(BottomNavigationView view, InverseBindingListener listener) {
        view.setOnNavigationItemSelectedListener(item -> {
            // Notify the listener when the item is selected
            listener.onChange();
            return true;
        });
    }

}