package com.gerardopoblete.hospitalapp.view.doctors;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardopoblete.hospitalapp.R;
import com.gerardopoblete.hospitalapp.model.dates.Date;
import com.gerardopoblete.hospitalapp.model.doctors.Doctor;
import com.gerardopoblete.hospitalapp.view.dates.DatesFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoctorsFragment extends Fragment implements DoctorsAdapter.Callback {

    @BindView(R.id.main_scroll_view)
    ScrollView mainScrollView;
    @BindView(R.id.til_rut)
    TextInputLayout tilRut;
    @BindView(R.id.til_names)
    TextInputLayout tilNames;
    @BindView(R.id.til_lastnames)
    TextInputLayout tilLastnames;
    @BindView(R.id.tv_rut)
    TextInputEditText tvRut;
    @BindView(R.id.tv_names)
    TextInputEditText tvNames;
    @BindView(R.id.tv_lastnames)
    TextInputEditText tvLastnames;
    @BindView(R.id.speciality_spinner)
    Spinner specialitySpinner;
    @BindView(R.id.rv_doctors_list)
    RecyclerView rvDoctorsList;
    @BindView(R.id.btn_save)
    Button btnSave;

    private Context context;
    public static List<Doctor> doctors;

    static {
        doctors = new ArrayList<>();
    }

    public static Fragment newFragment() {
        return new DoctorsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initProps();
        initView();
    }

    private void initProps() {
        context = getContext();
    }

    private void initView() {
        setUpSpecialitySpinner();
        setUpDoctorsList();
    }

    private void setUpSpecialitySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.speciality_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        specialitySpinner.setAdapter(adapter);
    }


    private void setUpDoctorsList() {
        DoctorsAdapter adapter = new DoctorsAdapter(this, doctors);
        rvDoctorsList.setLayoutManager(new LinearLayoutManager(context));
        rvDoctorsList.setAdapter(adapter);
    }

    @OnClick(R.id.btn_save)
    void onSaveButtonClicked() {
        boolean correctFields = validateFields();
        if (correctFields) {
            Doctor doctor = new Doctor(tvRut.getText().toString(), tvNames.getText().toString(), tvLastnames.getText().toString(), specialitySpinner.getSelectedItem().toString());
            if (btnSave.getText().equals(getString(R.string.modify))) {
                modifyDoctor(doctor);
                setUpViewToAdd();
            } else {
                saveNewDoctor(doctor);
            }
        }
    }

    private void setUpViewToAdd() {
        tvRut.setText(null);
        tvNames.setText(null);
        tvLastnames.setText(null);

        tvRut.setEnabled(true);
        tilRut.setEnabled(true);

        btnSave.setText(R.string.save);
    }

    private void modifyDoctor(Doctor newDoctor) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getRUT().equals(newDoctor.getRUT())) {
                doctors.set(i, newDoctor);
                Toast.makeText(context, getString(R.string.doctor_modified), Toast.LENGTH_SHORT).show();
                setUpDoctorsList();
                break;
            }
        }
    }

    private boolean validateFields() {
        boolean error = false;

        if (tvRut.getText().length() == 0) {
            tilRut.setError("Escriba un RUT válido.");
            error = true;
        } else {
            tilRut.setError(null);
        }

        if (tvNames.getText().length() < 3) {
            tilNames.setError("Escriba un nombre válido.");
            error = true;
        } else {
            tilNames.setError(null);
        }

        if (tvLastnames.getText().length() < 3) {
            tilLastnames.setError("Escriba un apellido válido.");
            error = true;
        } else {
            tilLastnames.setError(null);
        }

        return !error;
    }

    private void saveNewDoctor(Doctor newDoctor) {
        for (Doctor doctor : doctors) {
            if (doctor.getRUT().equals(newDoctor.getRUT())) {
                Toast.makeText(context, getString(R.string.doctor_repeated), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        doctors.add(newDoctor);
        setUpDoctorsList();
        Toast.makeText(context, getString(R.string.doctor_registered), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(Doctor doctor) {
        tvRut.setText(doctor.getRUT());
        tilRut.setEnabled(false);
        tvRut.setEnabled(false);

        tvNames.setText(doctor.getNames());
        tvLastnames.setText(doctor.getLastnames());

        int specialityIndex = 0;
        String[] specialities = getResources().getStringArray(R.array.speciality_array);
        for (String speciality : specialities) {
            if (speciality.equals(doctor.getSpeciality())) {
                break;
            }
            specialityIndex++;
        }

        specialitySpinner.setSelection(specialityIndex);

        btnSave.setText(R.string.modify);

        mainScrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    @Override
    public void removeItem(int index) {
        for (Date date : DatesFragment.dates) {
            if (date.getDoctorRUT().equals(doctors.get(index).getRUT())) {
                Toast.makeText(context, getString(R.string.doctor_in_dates), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        doctors.remove(index);
        setUpDoctorsList();
    }
}
