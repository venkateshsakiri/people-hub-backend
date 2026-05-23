package school.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "salary")
public class SalaryEntity extends BaseEntity{

    private String empId;

    @Column(name = "annual_ctc")
    private String annualCtc;

    @Column(name = "pay_cycle")
    private String payCycle;

    @Column(name = "basic_pay")
    private String basicPay;

    private String hra;

    @Column(name = "special_allowance")
    private String specialAllowance;

    private String pf;

    @Column(name = "variable_pay")
    private String variablePay;
}
