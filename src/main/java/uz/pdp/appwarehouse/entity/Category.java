package uz.pdp.appwarehouse.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.appwarehouse.entity.template.AbsEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Category extends AbsEntity {

    @ManyToOne
    private Category category;
}
