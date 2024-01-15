package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File src = new File("src/main/resources/tickets.json");

        JsonNode root = objectMapper.readTree(src);
        JsonNode ticketsNode = root.get("tickets");
        List<Ticket> tickets = objectMapper.treeToValue(ticketsNode, new TypeReference<>() {});

        tickets = tickets.stream()
                .filter(ticket -> "VVO".equals(ticket.getOrigin()))
                .filter(ticket -> "TLV".equals(ticket.getDestination()))
                .collect(Collectors.toList());

        Map<String, Optional<Ticket>> minDurations =
                tickets.stream()
                        .collect(Collectors.groupingBy(Ticket::getCarrier,
                                Collectors.minBy(Comparator.comparingInt(Ticket::getDurationInMinutes))));

        System.out.println("Minimal durations:");
        minDurations.forEach((key, value) -> {
            if (value.isPresent()) {
                int duration = value.get().getDurationInMinutes();
                System.out.println(key + ": " + (duration / 60) + " hours " + (duration % 60) + " minutes");
            }
        });

        if (!tickets.isEmpty()) {
            BigDecimal sum = tickets.stream().map(Ticket::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal avg = sum.divide(BigDecimal.valueOf(tickets.size()), 2, RoundingMode.HALF_UP);
            BigDecimal median = findMedian(tickets);
            System.out.println("Difference between mean and median price: " + avg.subtract(median));
        }
    }

    private static BigDecimal findMedian(List<Ticket> tickets) {
        List<BigDecimal> prices = tickets.stream()
                .map(Ticket::getPrice)
                .sorted()
                .collect(Collectors.toList());

        int length = prices.size();
        if (length % 2 != 0) {
            return prices.get(length / 2);
        } else {
            return prices.get(length / 2 - 1).add(prices.get(length / 2))
                    .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
        }
    }
}