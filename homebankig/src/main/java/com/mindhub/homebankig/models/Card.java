package com.mindhub.homebankig.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private Integer cvv;
    private LocalDateTime thruDate;
    private LocalDateTime fromDate;
    private Boolean existsCard = true;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {
    }

    public Card(String cardHolder, CardType type, CardColor color, String number, Integer cvv, LocalDateTime fromDate, LocalDateTime thruDate, Boolean existsCard) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.existsCard = existsCard;
    }
    public Long getId() {
        return id;
    }
    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardholder) {
        this.cardHolder = cardHolder;
    }
    public CardType getType() {
        return type;
    }
    public void setType(CardType type) {
        this.type = type;
    }
    public CardColor getColor() {
        return color;
    }
    public void setColor(CardColor color) {
        this.color = color;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public Integer getCvv() {
        return cvv;
    }
    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }
    public LocalDateTime getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }
    public LocalDateTime getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public Boolean getExistsCard() { return existsCard; }
    public void setExistsCard(Boolean existsCard) { this.existsCard = existsCard; }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

}