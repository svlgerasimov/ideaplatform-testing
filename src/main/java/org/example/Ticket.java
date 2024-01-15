package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Ticket {
    private final String origin;
    private final String originName;
    private final String destination;
    private final String destinationName;
    private final LocalDate departureDate;
    private final LocalTime departureTime;
    private final LocalDate arrivalDate;
    private final LocalTime arrivalTime;
    private final String carrier;
    private final int stops;
    private final BigDecimal price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Ticket(
            @JsonProperty("origin") String origin,
            @JsonProperty("origin_name") String originName,
            @JsonProperty("destination") String destination,
            @JsonProperty("destination_name") String destinationName,
            @JsonProperty("departure_date") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy")
            LocalDate departureDate,
            @JsonProperty("departure_time") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H:mm")
            LocalTime departureTime,
            @JsonProperty("arrival_date") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy")
            LocalDate arrivalDate,
            @JsonProperty("arrival_time") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H:mm")
            LocalTime arrivalTime,
            @JsonProperty("carrier") String carrier,
            @JsonProperty("stops") int stops,
            @JsonProperty("price") BigDecimal price
    ) {
        this.origin = origin;
        this.originName = originName;
        this.destination = destination;
        this.destinationName = destinationName;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.carrier = carrier;
        this.stops = stops;
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public String getOriginName() {
        return originName;
    }

    public String getDestination() {
        return destination;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public String getCarrier() {
        return carrier;
    }

    public int getStops() {
        return stops;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getDurationInMinutes() {
        LocalDateTime departure = departureDate.atTime(departureTime);
        LocalDateTime arrival = arrivalDate.atTime(arrivalTime);

        Duration duration = Duration.between(departure, arrival);
        return (int) (duration.getSeconds() / 60);
    }
}
