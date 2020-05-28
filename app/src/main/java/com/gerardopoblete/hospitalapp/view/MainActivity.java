package com.gerardopoblete.hospitalapp.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.gerardopoblete.hospitalapp.R;
import com.gerardopoblete.hospitalapp.view.dates.DatesFragment;
import com.gerardopoblete.hospitalapp.view.doctors.DoctorsFragment;
import com.gerardopoblete.hospitalapp.view.patients.PatientsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initBottomNavigationView();
        setUpView();
    }

    private void setUpView() {
        openDoctorsFragment();
    }

    private void initBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedItem -> {
            switch (selectedItem.getItemId()) {
                case R.id.btn_doctors:
                    openDoctorsFragment();
                    return true;
                case R.id.btn_patients:
                    openPatientsFragment();
                    return true;
                case R.id.btn_dates:
                    openDatesFragment();
                    return true;
                default:
                    return false;
            }
        });
    }

    private void openDoctorsFragment() {
        tvMainTitle.setText(R.string.doctors);
        openFragment(DoctorsFragment.newFragment());
    }

    private void openPatientsFragment() {
        tvMainTitle.setText(R.string.patients);
        openFragment(PatientsFragment.newFragment());
    }

    private void openDatesFragment() {
        tvMainTitle.setText(R.string.dates);
        openFragment(DatesFragment.newFragment());
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commitNowAllowingStateLoss();
    }
}
