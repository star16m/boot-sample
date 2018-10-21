package star16m.bootsample.resource.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import star16m.bootsample.resource.entity.AbstractEntity;

@NoRepositoryBean
public interface AbstractRepository<T extends AbstractEntity, I> extends JpaRepository<T, I> {
}
