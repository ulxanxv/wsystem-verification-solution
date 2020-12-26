package ru.monetarys.dao.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfer")
@EntityListeners(AuditingEntityListener.class)
public class Transfer {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @Length(max = 32)
    private String operationCode;

    @Column
    @NotNull
    private BigDecimal amount;

    @Column
    @NotNull
    @Length(max = 3)
    private String currency;

    @Column
    @Length(max = 36)
    private String transactionId;

    @Column
    @Length(max = 1024)
    private String description;

    @Column
    @Length(max = 1024)
    private String messageToPayee;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    @Column
    @Length(max = 22)
    private String payerAccount;

    @Column
    @Length(max = 128)
    private String payerLastName;

    @Column
    @Length(max = 128)
    private String payerFirstName;

    @Column
    @Length(max = 128)
    private String payerSubName;

    @Column
    @Length(max = 11)
    private String payerPhone;

    @Column
    @Length(max = 160)
    private String payeeBank;

    @Column
    @Length(max = 22)
    private String payeeAccount;

    @Column
    @Length(max = 128)
    private String payeeLastName;

    @Column
    @Length(max = 128)
    private String payeeFirstName;

    @Column
    @Length(max = 128)
    private String payeeSubName;

    @Column
    @Length(max = 11)
    private String payeePhone;

    @Column
    @NotNull
    @CreatedDate
    private LocalDateTime creationDate;

    @Column
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

}
