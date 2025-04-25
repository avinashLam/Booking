package com.example.BookingService.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
public class Booking {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BookingId;
    @NonNull
    private String name;
    @NonNull

    private double price;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
@JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;

    public int getBookingId() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        BookingId = bookingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
