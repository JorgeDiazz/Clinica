package com.gerardopoblete.hospitalapp.view.patients;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardopoblete.hospitalapp.R;
import com.gerardopoblete.hospitalapp.model.patients.Patient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.ViewHolder> {

    private List<Patient> patients;
    private Callback callback;

    PatientsAdapter(Callback callback, List<Patient> patients) {
        this.callback = callback;
        this.patients = patients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_patients_list, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Patient patient = patients.get(position);
        holder.setUpView(patient);
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_rut)
        TextView tvRut;
        @BindView(R.id.tv_names)
        TextView tvNames;
        @BindView(R.id.tv_lastnames)
        TextView tvLastnames;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_email)
        TextView tvEmail;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setUpView(Patient patient) {
            tvRut.setText(patient.getRUT());
            tvNames.setText(patient.getNames());
            tvLastnames.setText(patient.getLastnames());
            tvPhone.setText(patient.getPhone());
            tvEmail.setText(patient.getEmail());
            tvAddress.setText(patient.getAddress());
        }

        @OnClick(R.id.btn_remove)
        void onRemoveButtonClicked() {
            callback.removeItem(getLayoutPosition());
        }

        @OnClick(R.id.item_container)
        void onItemContainerClicked() {
            callback.onItemClicked(patients.get(getLayoutPosition()));
        }
    }

    interface Callback {
        void onItemClicked(Patient patient);

        void removeItem(int index);
    }
}
