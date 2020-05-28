package com.gerardopoblete.hospitalapp.view.doctors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardopoblete.hospitalapp.R;
import com.gerardopoblete.hospitalapp.model.doctors.Doctor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {

    private List<Doctor> doctors;
    private Callback callback;

    DoctorsAdapter(Callback callback, List<Doctor> doctors) {
        this.callback = callback;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_doctors_list, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Doctor doctor = doctors.get(position);
        holder.setUpView(doctor);
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_rut)
        TextView tvRut;
        @BindView(R.id.tv_names)
        TextView tvNames;
        @BindView(R.id.tv_lastnames)
        TextView tvLastnames;
        @BindView(R.id.tv_speciality)
        TextView tvSpeciality;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setUpView(Doctor doctor) {
            tvRut.setText(doctor.getRUT());
            tvNames.setText(doctor.getNames());
            tvLastnames.setText(doctor.getLastnames());
            tvSpeciality.setText(doctor.getSpeciality());
        }

        @OnClick(R.id.btn_remove)
        void onRemoveButtonClicked() {
            callback.removeItem(getLayoutPosition());
        }

        @OnClick(R.id.item_container)
        void onItemContainerClicked() {
            callback.onItemClicked(doctors.get(getLayoutPosition()));
        }
    }

    interface Callback {
        void onItemClicked(Doctor doctor);

        void removeItem(int index);
    }
}
