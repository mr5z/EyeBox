package com.nkraft.eyebox.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Visit;

import java.util.List;

public class VisitsAdapter extends BaseListAdapter<VisitsAdapter.ViewHolder, Visit> {

    public VisitsAdapter(List<Visit> dataList) {
        super(dataList, R.layout.row_visits);
    }

    @Override
    void onDataBind(ViewHolder holder, Visit data) {
        holder.txtDateVisit.setText(formatDate(data.getDateVisit()));
        Bitmap bitmap = BitmapFactory.decodeFile(data.getClientSignaturePath());
        holder.imgClientSignature.setImageBitmap(bitmap);
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDateVisit;
        ImageView imgClientSignature;

        public ViewHolder(View itemView) {
            super(itemView);
            txtDateVisit = itemView.findViewById(R.id.va_txt_date_visit);
            imgClientSignature = itemView.findViewById(R.id.va_img_client_signature);
        }
    }
}
