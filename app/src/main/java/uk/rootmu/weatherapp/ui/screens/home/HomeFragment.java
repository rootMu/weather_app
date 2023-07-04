package uk.rootmu.weatherapp.ui.screens.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import javax.inject.Inject;

import uk.rootmu.weatherapp.R;
import uk.rootmu.weatherapp.WeatherApp;
import uk.rootmu.weatherapp.databinding.FragmentHomeBinding;
import uk.rootmu.weatherapp.persistance.models.CityEntity;
import uk.rootmu.weatherapp.ui.screens.CityViewModel;

public class HomeFragment extends Fragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private CityViewModel viewModel;
    private FragmentHomeBinding binding;

    private ArrayAdapter arrayAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((WeatherApp) requireActivity().getApplication()).getAppComponent().inject(this);
        viewModel = new ViewModelProvider(this, viewModelFactory)
                .get(CityViewModel.class);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        setupAdapter();

        binding.seasonSelection.setOnItemSelectedListener(item -> {
            int position = 0;
            for (int i = 0; i < binding.seasonSelection.getMenu().size(); i++) {
                if (binding.seasonSelection.getMenu().getItem(i).getItemId() == item.getItemId()) {
                    position = i;
                    break;
                }
            }

            viewModel.setSeason(position);
            return true;
        });


        return binding.getRoot();
    }

    private void setupAdapter() {
        arrayAdapter = new ArrayAdapter(requireContext(), R.layout.dropdown_item, new ArrayList<>());
        binding.cityName.setOnItemClickListener((parent, view, position, id) -> {
            CityEntity selectedCity = (CityEntity) parent.getItemAtPosition(position);
            // Update the ViewModel with the selected city
            viewModel.setSelectedCity(selectedCity);
            binding.citySelection.setStartIconDrawable(convertTypeNameToDrawable(selectedCity.type));
        });

        viewModel.getAllCities().observe(getViewLifecycleOwner(), cities -> {
            arrayAdapter.clear();
            arrayAdapter.addAll(cities);
            binding.cityName.setAdapter(arrayAdapter);
        });
    }

    private int convertTypeNameToDrawable(String type) {
        return switch(type) {
            case "Small City" -> R.drawable.ic_small;
            case "Medium City" -> R.drawable.ic_medium;
            case "Large City" -> R.drawable.ic_large;
            default -> R.drawable.ic_small;
        };
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}