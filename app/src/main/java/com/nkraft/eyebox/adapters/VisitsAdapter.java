package com.nkraft.eyebox.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.Visit;
import com.nkraft.eyebox.utils.Formatter;

import java.util.List;

public class VisitsAdapter extends BaseListAdapter<VisitsAdapter.ViewHolder, Visit> {

    public VisitsAdapter(List<Visit> dataList) {
        super(dataList, R.layout.row_visits);
    }

    @Override
    void onDataBind(ViewHolder holder, Visit data) {
        Bitmap bitmap = BitmapFactory.decodeFile(data.getFileName());
        holder.txtDateVisit.setText(
                Formatter.string("%s %s",
                        Formatter.date(data.getDate()),
                        Formatter.time(data.getDate())));
        holder.txtClientName.setText(data.getClientName());
        holder.imgClientSignature.setImageBitmap(bitmap);
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDateVisit;
        TextView txtClientName;
        ImageView imgClientSignature;

        public ViewHolder(View itemView) {
            super(itemView);
            txtDateVisit = itemView.findViewById(R.id.va_txt_date_visit);
            txtClientName = itemView.findViewById(R.id.va_txt_client_name);
            imgClientSignature = itemView.findViewById(R.id.va_img_client_signature);
        }
    }
}
