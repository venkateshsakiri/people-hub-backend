package school.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "access_rights")
public class AccessRightsEntity extends BaseEntity{

    private String module;

    private String role;

    @Column(name = "is_view")
    private Boolean isView;

    @Column(name = "is_create")
    private Boolean isCreate;

    @Column(name = "is_edit")
    private Boolean isEdit;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "is_approve")
    private Boolean isApprove;

}
