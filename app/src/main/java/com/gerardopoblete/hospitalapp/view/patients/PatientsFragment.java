package com.gerardopoblete.hospitalapp.view.patients;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardopoblete.hospitalapp.R;
import com.gerardopoblete.hospitalapp.model.dates.Date;
import com.gerardopoblete.hospitalapp.model.patients.Patient;
import com.gerardopoblete.hospitalapp.view.dates.DatesFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PatientsFragment extends Fragment implements PatientsAdapter.Callback {

    @BindView(R.id.main_scroll_view)
    ScrollView mainScrollView;
    @BindView(R.id.til_rut)
    TextInputLayout tilRut;
    @BindView(R.id.til_names)
    TextInputLayout tilNames;
    @BindView(R.id.til_lastnames)
    TextInputLayout tilLastnames;
    @BindView(R.id.til_phone)
    TextInputLayout tilPhone;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.til_address)
    TextInputLayout tilAddress;
    @BindView(R.id.tv_rut)
    TextInputEditText tvRut;
    @BindView(R.id.tv_names)
    TextInputEditText tvNames;
    @BindView(R.id.tv_lastnames)
    TextInputEditText tvLastnames;
    @BindView(R.id.tv_phone)
    TextInputEditText tvPhone;
    @BindView(R.id.tv_email)
    TextInputEditText tvEmail;
    @BindView(R.id.tv_address)
    TextInputEditText tvAddress;
    @BindView(R.id.rv_patients_list)
    RecyclerView rvPatientsList;
    @BindView(R.id.btn_save)
    Button btnSave;

    private Context context;

    public static List<Patient> patients;

    static {
        patients = new ArrayList<>();
    }

    public static Fragment newFragment() {
        return new PatientsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patients, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initProps();
        initView();
    }

    private void initView() {
        setUpPatientsList();
    }

    private void setUpPatientsList() {
        PatientsAdapter adapter = new PatientsAdapter(this, patients);
        rvPatientsList.setLayoutManager(new LinearLayoutManager(context));
        rvPatientsList.setAdapter(adapter);
    }

    private void initProps() {
        context = getContext();
    }

    @OnClick(R.id.btn_save)
    void onSaveButtonClicked() {
        boolean correctFields = validateFields();
        if (correctFields) {
            Patient patient = new Patient(tvRut.getText().toString(), tvNames.getText().toString(), tvLastnames.getText().toString(), tvPhone.getText().toString(), tvEmail.getText().toString(), tvAddress.getText().toString());
            if (btnSave.getText().equals(getString(R.string.modify))) {
                modifyPatient(patient);
                setUpViewToAdd();
            } else {
                saveNewPatient(patient);
            }
        }
    }

    private void modifyPatient(Patient newPatient) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getRUT().equals(newPatient.getRUT())) {
                patients.set(i, newPatient);
                Toast.makeText(context, getString(R.string.patient_modified), Toast.LENGTH_SHORT).show();
                setUpPatientsList();
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

        if (tvPhone.getText().length() != 9) {
            tilPhone.setError("Escriba un teléfono válido.");
            error = true;
        } else {
            tilPhone.setError(null);
        }

        if (tvEmail.getText().length() < 10 || !tvEmail.getText().toString().contains("@")) {
            tilEmail.setError("Escriba un email válido.");
            error = true;
        } else {
            tilEmail.setError(null);
        }

        if (tvAddress.getText().length() < 15) {
            tilAddress.setError("Escriba una dirección válida.");
            error = true;
        } else {
            tilAddress.setError(null);
        }
        return !error;
    }


    private void saveNewPatient(Patient newPatient) {
        for (Patient patient : patients) {
            if (patient.getRUT().equals(newPatient.getRUT())) {
                Toast.makeText(context, getString(R.string.patient_repeated), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        patients.add(newPatient);
        setUpPatientsList();
        Toast.makeText(context, getString(R.string.patient_registered), Toast.LENGTH_SHORT).show();
    }

    private void setUpViewToAdd() {
        tvRut.setText(null);
        tvNames.setText(null);
        tvLastnames.setText(null);
        tvPhone.setText(null);
        tvEmail.setText(null);
        tvAddress.setText(null);

        tvRut.setEnabled(true);
        tilRut.setEnabled(true);

        btnSave.setText(R.string.save);
    }

    @Override
    public void onItemClicked(Patient patient) {
        tvRut.setText(patient.getRUT());
        tilRut.setEnabled(false);
        tvRut.setEnabled(false);

        tvNames.setText(patient.getNames());
        tvLastnames.setText(patient.getLastnames());
        tvPhone.setText(patient.getPhone());
        tvEmail.setText(patient.getEmail());
        tvAddress.setText(patient.getAddress());

        btnSave.setText(R.string.modify);

        mainScrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    @Override
    public void removeItem(int index) {
        for (Date date : DatesFragment.dates) {
            if (date.getDoctorRUT().equals(patients.get(index).getRUT())) {
                Toast.makeText(context, getString(R.string.patient_in_dates), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        patients.remove(index);
        setUpPatientsList();
    }
}
