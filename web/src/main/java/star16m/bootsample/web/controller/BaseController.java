package star16m.bootsample.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import star16m.bootsample.core.error.ResourceNotfoundException;
import star16m.bootsample.core.utils.SimpleUtil;
import star16m.bootsample.web.common.SimpleResponse;
import star16m.bootsample.web.controller.annotations.SimpleRestMethod;
import star16m.bootsample.web.controller.annotations.SimpleRestMethodMapping;
import star16m.bootsample.web.resource.BaseResource;
import star16m.bootsample.web.resource.sample.Resource;
import star16m.bootsample.web.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api
@Slf4j
public abstract class BaseController<T extends BaseResource, I extends Integer> {
    private Resource resource;
    private BaseService<T, I> service;
    protected BaseController(Resource resource, BaseService<T, I> service) {
        this.resource = resource;
        this.service = service;
    }
    @ApiOperation(value = "전체 목록 조회")
    @GetMapping
    @SimpleRestMethodMapping(SimpleRestMethod.FIND_ALL)
    public final SimpleResponse<List<T>> findAll() {
        return SimpleResponse.of(this.service.findAll());
    }

    @ApiOperation(value = "상세 조회")
    @GetMapping(value="/{id}")
    @SimpleRestMethodMapping(SimpleRestMethod.FIND_ONE)
    public final SimpleResponse<T> findById(@PathVariable final I id) {
        log.debug("try get {} with id [{}]", this.resource, id);
        T object = getObject(id);
        if (object == null) {
            throw new ResourceNotfoundException(this.getClass().getSimpleName());
        }
        return SimpleResponse.of(object);
    }

    @ApiOperation(value = "생성")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SimpleRestMethodMapping(SimpleRestMethod.CREATE)
    public final SimpleResponse<T> create(@RequestBody final T createObject) {
        SimpleUtil.mustNotNull(createObject);
        log.debug("try create {} with [{}]", this.resource.getDescription(), createObject);
        T createdObject = this.service.save(createObject);
        return SimpleResponse.of(createdObject);
    }

    @ApiOperation(value = "갱신")
    @PostMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SimpleRestMethodMapping(SimpleRestMethod.UPDATE)
    public final SimpleResponse<T> update(@PathVariable final I id, @RequestBody final Map<String, Object> newObjectMap) {
        SimpleUtil.mustNotNull(id);
        SimpleUtil.mustNotNull(newObjectMap);
        SimpleUtil.must(newObjectMap.keySet().size(), (size) -> size <= 0, "not found some update properties.");
        log.debug("try update {} with id [{}] to [{}]", this.resource.getDescription(), id, newObjectMap);
        T updatedEntity = this.service.update(id, newObjectMap);
        log.debug("updated with [{}]", updatedEntity);
        return SimpleResponse.of(updatedEntity);
    }

    @ApiOperation(value = "삭제")
    @DeleteMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SimpleRestMethodMapping(SimpleRestMethod.DELETE)
    public final SimpleResponse<Boolean> delete(@PathVariable final I id) {
        this.service.delete(id);
        return SimpleResponse.of(true);
    }

    private T getObject(final I id) {
        Optional<T> optionalObject = this.service.findOne(id);
        // return null
        return optionalObject.orElse(null);
    }
}
