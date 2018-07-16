package com.nkraft.eyebox.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.nkraft.eyebox.utils.Formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "Orders")
public class Order implements Parcelable, IModel {

    @Ignore
    private static long idCounter;

    @PrimaryKey
    private long id;
    private int quantity;
    private long dateOrdered;
    private String clientName;
    @Embedded
    private Order.Product product;

    public Order() {

    }

    @Ignore
    public Order(Order.Product product, int quantity) {
        this.id = generateId();
        this.quantity = quantity;
        this.product = product;
    }

    @Ignore
    protected Order(Parcel in) {
        id = in.readLong();
        quantity = in.readInt();
        dateOrdered = in.readLong();
        clientName = in.readString();
        product = in.readParcelable(Product.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private static long generateId() {
        return idCounter++;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeInt(quantity);
        parcel.writeLong(dateOrdered);
        parcel.writeString(clientName);
        parcel.writeParcelable(product, i);
    }

    public long getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(long dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getFormattedDateOrdered() {
        return Formatter.date(dateOrdered);
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public static class Product implements Parcelable {

        @ColumnInfo(name = "product_id")
        private long id;
        @ColumnInfo(name = "product_name")
        private String name;
        @ColumnInfo(name = "product_generic_name")
        private String genericName;
        @ColumnInfo(name = "product_unit")
        private String unit;

        public Product() {}

        protected Product(Parcel in) {
            id = in.readLong();
            name = in.readString();
            genericName = in.readString();
            unit = in.readString();
        }

        public static final Creator<Product> CREATOR = new Creator<Product>() {
            @Override
            public Product createFromParcel(Parcel in) {
                return new Product(in);
            }

            @Override
            public Product[] newArray(int size) {
                return new Product[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(id);
            parcel.writeString(name);
            parcel.writeString(genericName);
            parcel.writeString(unit);
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGenericName() {
            return genericName;
        }

        public void setGenericName(String genericName) {
            this.genericName = genericName;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
