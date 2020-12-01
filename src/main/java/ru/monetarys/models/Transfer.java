package ru.monetarys.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, nullable = false)
    private String operationCode;

    @Column(length = 36)
    private String transactionId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(length = 3, nullable = false)
    private String currency;

    @Column(length = 1024)
    private String description;

    @Column(length = 1024)
    private String messageToPayee;

    @Column(length = 30, nullable = false)
    private String status;

    @Column(length = 22)
    private String payerAccount;

    @Column(length = 128)
    private String payerLastName;

    @Column(length = 128)
    private String payerFirstName;

    @Column(length = 128)
    private String payerSubName;

    @Column(length = 11)
    private String payerPhone;

    @Column(length = 160)
    private String payeeBank;

    @Column(length = 22)
    private String payeeAccount;

    @Column(length = 128)
    private String payeeLastName;

    @Column(length = 128)
    private String payeeFirstName;

    @Column(length = 128)
    private String payeeSubName;

    @Column(length = 11)
    private String payeePhone;

    @Column(nullable = false)
    private Date creationDate;

    private Date lastUpdateDate;

}
