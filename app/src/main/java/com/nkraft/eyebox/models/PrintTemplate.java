package com.nkraft.eyebox.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.nkraft.eyebox.services.FontStyle;
import com.nkraft.eyebox.services.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class PrintTemplate implements Parcelable, IModel {

    private static long idGenerator;

    private long id;
    private String title;
    private byte[] background;
    private List<Data> printData = new ArrayList<>();
    private String htmlHeader;
    private String htmlFooter;

    protected PrintTemplate(Parcel in) {
        title = in.readString();
        background = in.createByteArray();
        printData = in.createTypedArrayList(Data.CREATOR);
        htmlHeader = in.readString();
        htmlFooter = in.readString();
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

    public byte[] getBackground() {
        return background;
    }

    public void setBackground(byte[] background) {
        this.background = background;
    }

    public PrintTemplate() {
        id = ++idGenerator;
    }

    public PrintTemplate(String title, byte[] background) {
        id = ++idGenerator;
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

    public void addPrintData(String line, FontStyle fontStyle) {
        Data data = new Data();
        data.setLine(line);
        data.setFontStyle(fontStyle);
        printData.add(data);
    }

    public void addPrintData(String line, FontStyle fontStyle, TextAlignment alignment) {
        Data data = new Data();
        data.setLine(line);
        data.setFontStyle(fontStyle);
        data.setAlignment(alignment);
        printData.add(data);
    }

    public void addPrintData(Bitmap bitmap) {
        Data data = new Data();
        data.setImage(bitmap);
        printData.add(data);
    }

    public List<String> getDataLines() {
        List<String> lines = new ArrayList<>();
        for (Data data : printData) {
            String line = data.getLine();
            if (line != null)
                lines.add(line);
        }
        return lines;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeByteArray(background);
        parcel.writeTypedList(printData);
        parcel.writeString(htmlHeader);
        parcel.writeString(htmlFooter);
    }

    public String getHtmlHeader() {
        return htmlHeader;
    }

    public void setHtmlHeader(String htmlHeader) {
        this.htmlHeader = htmlHeader;
    }

    public String getHtmlFooter() {
        return htmlFooter;
    }

    public void setHtmlFooter(String htmlFooter) {
        this.htmlFooter = htmlFooter;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public static class Data implements Parcelable {
        private String line;
        private Bitmap image;
        private TextAlignment alignment = TextAlignment.LEFT;
        private FontStyle fontStyle = FontStyle.BOLD;

        public Data() {}

        protected Data(Parcel in) {
            line = in.readString();
            image = in.readParcelable(Bitmap.class.getClassLoader());
            int alignmentIndex = in.readInt();
            int fontStyleIndex = in.readInt();
            alignment = TextAlignment.values()[alignmentIndex];
            fontStyle = FontStyle.values()[fontStyleIndex];
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

        public FontStyle getFontStyle() {
            return fontStyle;
        }

        public void setFontStyle(FontStyle fontStyle) {
            this.fontStyle = fontStyle;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(line);
            parcel.writeParcelable(image, i);
            parcel.writeInt(alignment.ordinal());
            parcel.writeInt(fontStyle.ordinal());
        }
    }
}
