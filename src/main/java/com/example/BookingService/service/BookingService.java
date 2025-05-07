package com.example.BookingService.service;

import com.example.BookingService.entity.Booking;
import com.example.BookingService.entity.Person;
import com.example.BookingService.exception.CustomIdNotFound;

import com.example.BookingService.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
     RestTemplate restTemplate;
    private final BookingRepository bookingRepository;
    @Autowired
     JavaMailSender javaMailSender;



    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;



    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.username}")
    private String username;





    public BookingService( BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking saveBooking(Booking booking){
        Booking booking1=bookingRepository.save(booking);
        return booking1;
    }

    public String deleteBooking(Integer id){
        Optional<Booking> booking= Optional.ofNullable(bookingRepository.findById(id).orElse(null));
        bookingRepository.deleteById(booking.get().getBookingId());
        return "id deleted";
    }
    public Booking getBooking(Integer id){
        if(id==0){
            throw new CustomIdNotFound("msg");
        }
            Booking booking2 = bookingRepository.findById(id).orElse(null);
            Booking booking1 = new Booking();
            booking1.setBookingId(booking2.getBookingId());
            booking1.setName(booking2.getName());
            booking1.setPrice(booking2.getPrice());
            booking1.setDate(booking2.getDate());
            booking1.setEmail(booking2.getEmail());


        return booking2;
    }

    public Booking updateBooking(Integer id, Booking booking) {
        Booking booking2 = bookingRepository.findById(id).orElse(null);

        if (booking2 == null) {
            throw new EntityNotFoundException("Booking with ID " + id + " not found");
        }

        booking2.setBookingId(id);
        booking2.setName(booking.getName());
        booking2.setPrice(booking.getPrice());
        booking2.setDate(booking.getDate());
        booking2.setEmail(booking.getEmail());

        bookingRepository.save(booking2);


            SimpleMailMessage message = new SimpleMailMessage();
           message.setFrom(username);
            message.setTo(booking2.getEmail());
            message.setSubject("Booking Updated");
            message.setText("Booking Name: " + booking2.getName() + "\nBooking Date: " + booking2.getDate());

            javaMailSender.send(message);


        return booking2;
    }


    public Booking getBookingByIdAndName(Integer bookingId, String name) {
        Optional<Booking> bookingOptional = bookingRepository.findByBookingIdAndName(bookingId, name);

        return bookingOptional.orElse(null);  // Return null if no booking found
    }

    public List<Booking> getAll() {
        return  bookingRepository.getCustom();
    }
    public Page<Booking> getBookingsWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return bookingRepository.findAll(pageable);
    }

    public Booking create(Booking booking) {
        for (Person person : booking.getPersonList()) {
            person.setBooking(booking); // Important: Set the back-reference
        }
        return bookingRepository.save(booking);
    }
    public List<Booking> bookingList(){
        return bookingRepository.findAllBookingsWithPersons();
    }
}
