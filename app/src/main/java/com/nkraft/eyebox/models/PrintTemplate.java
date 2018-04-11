package com.nkraft.eyebox.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.nkraft.eyebox.services.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class PrintTemplate implements Parcelable {

    private String title;

    private int background;

    private List<Data> printData = new ArrayList<>();

    protected PrintTemplate(Parcel in) {
        title = in.readString();
        background = in.readInt();
        printData = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<PrintTemplate> CREATOR = new Creator<PrintTemplate>() {
        @Override
        public PrintTemplate createFromParcel(Parcel in) {
            return new PrintTemplate(in);
        }

        @Override
        public PrintTemplate[] newArray(int size) {
            return new PrintTemplate[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public PrintTemplate() {}

    public PrintTemplate(String title, int background) {
        this.title = title;
        this.background = background;
    }

    public List<Data> getPrintData() {
        return printData;
    }

    public void addPrintData(String line) {
        Data data = new Data();
        data.setLine(line);
        data.setAlignment(TextAlignment.LEFT);
        printData.add(data);
    }

    public void addPrintData(String line, TextAlignment alignment) {
        Data data = new Data();
        data.setLine(line);
        data.setAlignment(alignment);
        printData.add(data);
    }

    public void addPrintData(Bitmap bitmap) {
        Data data = new Data();
        data.setImage(bitmap);
        printData.add(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(background);
        parcel.writeTypedList(printData);
    }

    public static class Data implements Parcelable {
        private String line;
        private Bitmap image;
        private TextAlignment alignment;

        public Data() {}

        protected Data(Parcel in) {
            line = in.readString();
            image = in.readParcelable(Bitmap.class.getClassLoader());
            alignment = TextAlignment.values()[in.readInt()];
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public Bitmap getImage() {
            return image;
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }

        public TextAlignment getAlignment() {
            return alignment;
        }

        public void setAlignment(TextAlignment alignment) {
            this.alignment = alignment;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(line);
            parcel.writeParcelable(image, i);
            parcel.writeInt(alignment != null ? alignment.ordinal() : 0);
        }
    }
}
