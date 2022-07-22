package id.holigo.services.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirlinesTransactionTripItineraryDto implements Serializable {

    private String pnr;

    private Integer leg;

    private String airlinesName;

    private String flightNumber;

    private AirportDto originAirport;

    private AirportDto destinationAirport;

    private Date departureDate;

    private Time departureTime;

    private Date arrivalDate;

    private Time arrivalTime;

    private Integer duration;

    private String imageUrl;

    private Integer transit;
}
