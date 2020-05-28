package com.gerardopoblete.hospitalapp.view.dates;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardopoblete.hospitalapp.R;
import com.gerardopoblete.hospitalapp.model.dates.Date;
import com.gerardopoblete.hospitalapp.model.doctors.Doctor;
import com.gerardopoblete.hospitalapp.model.patients.Patient;
import com.gerardopoblete.hospitalapp.view.doctors.DoctorsFragment;
import com.gerardopoblete.hospitalapp.view.patients.PatientsFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DatesFragment extends Fragment implements DatesAdapter.Callback {

    @BindView(R.id.main_scroll_view)
    ScrollView mainScrollView;
    @BindView(R.id.til_id)
    TextInputLayout tilID;
    @BindView(R.id.rut_doctor_spinner)
    Spinner rutDoctorSpinner;
    @BindView(R.id.rut_patient_spinner)
    Spinner rutPatientSpinner;
    @BindView(R.id.state_spinner)
    Spinner stateSpinner;
    @BindView(R.id.tv_date_and_hour)
    TextView tvDateAndHour;
    @BindView(R.id.tv_id)
    TextInputEditText tvID;
    @BindView(R.id.btn_date_and_time)
    Button btnDateAndTime;
    @BindView(R.id.rv_dates_list)
    RecyclerView rvDatesList;
    @BindView(R.id.btn_save)
    Button btnSave;

    private Context context;
    private Calendar dateAndHour;
    public static List<Date> dates;

    static {
        dates = new ArrayList<>();
    }

    public static Fragment newFragment() {
        return new DatesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dates, container, false);
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
        setUpRutDoctorSpinner();
        setUpRutPatientSpinner();
        setUpStateSpinner();
    }

    private void setUpRutDoctorSpinner() {
        if (DoctorsFragment.doctors != null) {
            List<String> doctorRuts = new ArrayList<>();
            for (Doctor doctor : DoctorsFragment.doctors) {
                doctorRuts.add(doctor.getRUT());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_item, doctorRuts);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            rutDoctorSpinner.setAdapter(dataAdapter);
        }
    }

    private void setUpRutPatientSpinner() {
        if (PatientsFragment.patients != null) {
            List<String> patientRuts = new ArrayList<>();
            for (Patient patient : PatientsFragment.patients) {
                patientRuts.add(patient.getRUT());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_item, patientRuts);

            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            rutPatientSpinner.setAdapter(dataAdapter);
        }
    }


    private void setUpStateSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.state_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(adapter);
    }

    @OnClick(R.id.btn_date_and_time)
    void onDateAndTimeButtonClicked() {
        showDateTimePicker();
    }

    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        dateAndHour = Calendar.getInstance();
        new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            dateAndHour.set(year, monthOfYear, dayOfMonth);
            new TimePickerDialog(context, (mView, hourOfDay, minute) -> {
                dateAndHour.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateAndHour.set(Calendar.MINUTE, minute);
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                tvDateAndHour.setText(format.format(dateAndHour.getTime()));
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    @OnClick(R.id.btn_save)
    void onSaveButtonClicked() {
        boolean correctFields = validateFields();
        if (correctFields) {
            Date date = new Date(Long.parseLong(tvID.getText().toString()), rutDoctorSpinner.getSelectedItem().toString(), rutPatientSpinner.getSelectedItem().toString(), dateAndHour, stateSpinner.getSelectedItem().toString());
            if (btnSave.getText().equals(getString(R.string.modify))) {
                modifyDate(date);
                setUpViewToAdd();
            } else {
                saveNewDate(date);
            }
        }
    }


    private void setUpViewToAdd() {
        tvID.setText(null);
        tvDateAndHour.setText(R.string.date_and_hour_format);

        tvID.setEnabled(true);
        tilID.setEnabled(true);
        rutDoctorSpinner.setEnabled(true);
        rutPatientSpinner.setEnabled(true);
        btnDateAndTime.setEnabled(true);

        btnSave.setText(R.string.save);
    }

    private void modifyDate(Date newDate) {
        for (int i = 0; i < dates.size(); i++) {
            if (dates.get(i).getID() == newDate.getID()) {
                dates.set(i, newDate);
                Toast.makeText(context, getString(R.string.date_modified), Toast.LENGTH_SHORT).show();
                setUpDatesList();
                break;
            }
        }
    }

    private void setUpDatesList() {
        DatesAdapter adapter = new DatesAdapter(this, dates);
        rvDatesList.setLayoutManager(new LinearLayoutManager(context));
        rvDatesList.setAdapter(adapter);
    }

    private boolean validateFields() {
        boolean error = false;

        if (rutDoctorSpinner.getSelectedItem() == null || rutPatientSpinner.getSelectedItem() == null) {
            Toast.makeText(context, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show();
            error = true;
        }

        if (tvID.getText().length() == 0) {
            tilID.setError("Escriba un ID válido.");
            error = true;
        } else {
            tilID.setError(null);
        }

        if (dateAndHour == null) {
            Toast.makeText(context, getString(R.string.choose_date_and_hour), Toast.LENGTH_SHORT).show();
            error = true;
        }

        return !error;
    }

    private void saveNewDate(Date newDate) {
        for (Date date : dates) {
            if (date.getID() == newDate.getID()) {
                Toast.makeText(context, getString(R.string.date_repeated), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        dates.add(newDate);
        setUpDatesList();
        Toast.makeText(context, getString(R.string.date_registered), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(Date date) {
        tilID.setEnabled(false);
        tvID.setEnabled(false);

        rutDoctorSpinner.setEnabled(false);
        rutPatientSpinner.setEnabled(false);

        btnDateAndTime.setEnabled(false);

        int rutDoctorIndex = 0;
        for (Doctor doctor : DoctorsFragment.doctors) {
            if (date.getDoctorRUT().equals(doctor.getRUT())) {
                break;
            }
            rutDoctorIndex++;
        }

        rutDoctorSpinner.setSelection(rutDoctorIndex);


        int rutPatientIndex = 0;
        for (Patient patient : PatientsFragment.patients) {
            if (date.getPatientRUT().equals(patient.getRUT())) {
                break;
            }
            rutPatientIndex++;
        }

        rutPatientSpinner.setSelection(rutPatientIndex);


        btnSave.setText(R.string.modify);

        mainScrollView.fullScroll(ScrollView.FOCUS_UP);
    }
}
