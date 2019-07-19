    package star16m.bootsample.web.service;

import star16m.bootsample.core.error.ResourceNotfoundException;
import star16m.bootsample.core.error.SimpleException;
import star16m.bootsample.web.resource.BaseResource;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.bootsample.web.service.repository.BaseJpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseJpaService<T extends BaseResource, I> implements BaseService<T,I> {
    private BaseJpaRepository<T, I> simpleRepository;

    public BaseJpaService(BaseJpaRepository simpleRepository) {
        this.simpleRepository = simpleRepository;
    }

    public List<T> findAll() {
        return simpleRepository.findAll();
    }

    public Optional<T> findOne(I id) {
        SimpleUtil.mustNotNull(id);
        return this.simpleRepository.findById(id);
    }

    public T save(T object) {
        // object must not null
        SimpleUtil.mustNotNull(object);
        // object#id must not null
        SimpleUtil.mustNotNull(object.getId());
        return this.simpleRepository.save(object);
    }

    public T update(I id, Map<String, Object> map) {
        // object#id must not null
        SimpleUtil.mustNotNull(id);
        return simpleRepository.findById(id)
                .map(o -> {
                    patchedObject(o, map);
                    return simpleRepository.saveAndFlush(o);
                })
                .orElseThrow(() -> new ResourceNotfoundException(id.toString()));
    }

    public void delete(I id) throws SimpleException {
        SimpleUtil.mustNotNull(id);
        this.simpleRepository.deleteById(id);
    }

    public abstract void patchedObject(final T o, final Map<String, Object> map);
}
