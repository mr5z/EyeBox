package com.nkraft.eyebox.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.PrintTemplate;

import java.util.List;

public class PrintTemplateAdapter extends BaseListAdapter<PrintTemplateAdapter.ViewHolder, PrintTemplate> {

    public PrintTemplateAdapter(List<PrintTemplate> dataList) {
        super(dataList, R.layout.row_print_template);
    }

    @Override
    void onDataBind(ViewHolder holder, PrintTemplate data) {
        holder.txtTitle.setText(data.getTitle());
        holder.imgBackground.setImageResource(data.getBackground());
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        ImageView imgBackground;

        ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.pt_txt_title);
            imgBackground = itemView.findViewById(R.id.pt_img_background);
        }
    }
}
