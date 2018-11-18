package star16m.bootsample.resource.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import star16m.bootsample.resource.entity.BaseEntity;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, I> extends JpaRepository<T, I> {
}
