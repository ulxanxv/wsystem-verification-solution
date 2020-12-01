package ru.monetarys.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transfer")
public class Transfer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 32)
    @NotNull
    private String operationCode;

    @NotNull
    private BigDecimal amount;

    @Length(max = 3)
    @NotNull
    private String currency;

    @Length(max = 36)
    private String transactionId;

    @Length(max = 1024)
    private String description;

    @Length(max = 1024)
    private String messageToPayee;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TransferStatus status;

    @Length(max = 22)
    private String payerAccount;

    @Length(max = 128)
    private String payerLastName;

    @Length(max = 128)
    private String payerFirstName;

    @Length(max = 128)
    private String payerSubName;

    @Length(max = 11)
    private String payerPhone;

    @Length(max = 160)
    private String payeeBank;

    @Length(max = 22)
    private String payeeAccount;

    @Length(max = 128)
    private String payeeLastName;

    @Length(max = 128)
    private String payeeFirstName;

    @Length(max = 128)
    private String payeeSubName;

    @Length(max = 11)
    private String payeePhone;

    @CreatedDate
    @NotNull
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

}
