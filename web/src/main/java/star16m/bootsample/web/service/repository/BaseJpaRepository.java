package star16m.bootsample.web.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import star16m.bootsample.web.resource.BaseResource;

@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseResource, I> extends JpaRepository<T, I> {
}
