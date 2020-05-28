package com.gerardopoblete.hospitalapp.view.dates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardopoblete.hospitalapp.R;
import com.gerardopoblete.hospitalapp.model.dates.Date;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DatesAdapter extends RecyclerView.Adapter<DatesAdapter.ViewHolder> {

    private List<Date> dates;
    private Callback callback;

    DatesAdapter(Callback callback, List<Date> dates) {
        this.callback = callback;
        this.dates = dates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_dates_list, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Date date = dates.get(position);
        holder.setUpView(date);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_id)
        TextView tvID;
        @BindView(R.id.tv_rut_of_doctor)
        TextView tvRutOfDoctor;
        @BindView(R.id.tv_rut_of_patient)
        TextView tvRutOfPatient;
        @BindView(R.id.tv_date_and_hour)
        TextView tvDateAndHour;
        @BindView(R.id.tv_state)
        TextView tvState;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setUpView(Date date) {
            tvID.setText(String.valueOf(date.getID()));
            tvRutOfDoctor.setText(date.getDoctorRUT());
            tvRutOfPatient.setText(date.getPatientRUT());
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
            tvDateAndHour.setText(format.format(date.getDateAndTime().getTime()));
            tvState.setText(date.getState());
        }

        @OnClick(R.id.item_container)
        void onItemContainerClicked() {
            callback.onItemClicked(dates.get(getLayoutPosition()));
        }
    }

    interface Callback {
        void onItemClicked(Date date);
    }
}
