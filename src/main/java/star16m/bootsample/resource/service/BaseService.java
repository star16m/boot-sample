package star16m.bootsample.resource.service;

import star16m.bootsample.resource.entity.BaseEntity;
import star16m.bootsample.resource.service.error.EntityNotfoundException;
import star16m.bootsample.resource.service.error.SimpleException;
import star16m.bootsample.resource.utils.SimpleUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity, I> {
    private BaseRepository<T, I> simpleRepository;

    public BaseService(BaseRepository simpleRepository) {
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
                    return simpleRepository.save(o);
                })
                .orElseThrow(() -> new EntityNotfoundException(id.toString()));
    }

    public void delete(I id) throws SimpleException {
        SimpleUtil.mustNotNull(id);
        this.simpleRepository.deleteById(id);
    }

    protected abstract void patchedObject(final T o, final Map<String, Object> map);
}
