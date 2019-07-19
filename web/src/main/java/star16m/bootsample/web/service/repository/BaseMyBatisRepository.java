package star16m.bootsample.web.service.repository;

import star16m.bootsample.web.resource.BaseResource;

import java.util.List;

public interface BaseMyBatisRepository<T extends BaseResource, I> {
    List<T> findAll();

    T findById(I id);

    T save(T resource);

    void deleteById(I id);
}
