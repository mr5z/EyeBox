package com.nkraft.eyebox.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nkraft.eyebox.R;
import com.nkraft.eyebox.models.PrintTemplate;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PrintTemplateAdapter extends BaseListAdapter<PrintTemplateAdapter.ViewHolder, PrintTemplate> {

    public PrintTemplateAdapter(List<PrintTemplate> dataList) {
        super(dataList, R.layout.row_print_template);
    }

    @Override
    void onDataBind(ViewHolder holder, PrintTemplate data) {
        holder.txtTitle.setText(data.getTitle());
        byte[] background = data.getBackground();
        if (background != null) {
            Bitmap bitmap = toBitmap(background);
            bitmap = resizeBitmap(bitmap);
            holder.imgBackground.setImageBitmap(bitmap);
        }
    }

    @Override
    ViewHolder onCreateRow(View rowView) {
        return new ViewHolder(rowView);
    }

    private Bitmap toBitmap(byte[] pixels) {
        return BitmapFactory.decodeByteArray(pixels, 0, pixels.length);
    }

    private Bitmap resizeBitmap(Bitmap bitmap) {
        float aspectRatio = bitmap.getWidth() / (float) bitmap.getHeight();
        int height = 128;
        int width = Math.round(height * aspectRatio);
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
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
