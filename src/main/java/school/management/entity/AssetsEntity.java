package school.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "assets")
public class AssetsEntity extends BaseEntity{

    private String empId;

    @Column(name = "asset_name")
    private String assetName;

    private String quantity;

    private String type;

    private String model;

    private String assignedDate;

    private String condition;

    @Column(name = "asset_value")
    private String assetValue;

    private Boolean isReturnable=true;
}
