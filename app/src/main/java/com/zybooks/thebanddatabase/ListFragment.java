package com.zybooks.thebanddatabase;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import java.util.List;

public class ListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        LinearLayout layout = (LinearLayout) rootView;
        Button mSettingsbtn = new Button(new ContextThemeWrapper(getContext(), R.style.buttonStyle));
        mSettingsbtn.setText("Settings");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 10);   // 10px bottom margin

        mSettingsbtn.setLayoutParams(layoutParams);
        layout.addView(mSettingsbtn);

        mSettingsbtn.setOnClickListener(btn -> {
            Navigation.findNavController(btn).navigate(R.id.settingsFragment);
        });

        // Create the buttons using the band names and ids from BandRepository
        List<Band> bandList = BandRepository.getInstance(requireContext()).getBands();
        for (Band band : bandList) {
            Button button = new Button(getContext());

            button.setLayoutParams(layoutParams);

            // Display band's name on button
            button.setText(band.getName());
            button.setTag(band.getId());

            // Navigate to detail screen when clicked
            button.setOnClickListener(buttonView -> {
                // Create fragment arguments containing the selected band ID
                int selectedBandId = (int) buttonView.getTag();
                Bundle args = new Bundle();
                args.putInt(DetailFragment.ARG_BAND_ID, selectedBandId);

                // Send band ID to DetailFragment
                Navigation.findNavController(buttonView).navigate(R.id.show_item_detail, args);
            });

            // Add button to the LinearLayout

            layout.addView(button);
        }

        return rootView;
    }
}
