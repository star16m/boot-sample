package star16m.bootsample.resource.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@MappedSuperclass
@NoArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@ToString
public abstract class AbstractEntity {
    @Id
    @Column(name = "id")
    @Setter
    private Integer id;
    @Column(name = "name")
    @Setter
    private String name;
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate = LocalDateTime.now();
    @Column(name = "update_date")
    private LocalDateTime updateDate = LocalDateTime.now();

    @PrePersist
    public void onPrePersist() {
        this.createDate = LocalDateTime.now(ZoneOffset.systemDefault());
        this.updateDate = this.createDate;
    }
    @PreUpdate
    public void onPreUpdate() {
        this.updateDate = LocalDateTime.now(ZoneOffset.systemDefault());
    }
}
