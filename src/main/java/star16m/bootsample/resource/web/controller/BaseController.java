package star16m.bootsample.resource.web.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import star16m.bootsample.resource.Resource;
import star16m.bootsample.resource.entity.BaseEntity;
import star16m.bootsample.resource.service.BaseService;
import star16m.bootsample.resource.service.error.EntityNotfoundException;
import star16m.bootsample.resource.utils.SimpleUtil;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestMethod;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestMethodMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseController<T extends BaseEntity, I extends Integer> extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(BaseController.class);
    private Resource resource;
    private BaseService<T, I> service;
    BaseController(Resource resource, BaseService<T, I> service) {
        this.resource = resource;
        this.service = service;
    }
    @ApiOperation(value = "전체 목록 조회")
    @GetMapping
    @SimpleRestMethodMapping(SimpleRestMethod.FIND_ALL)
    public final ResponseEntity<List<T>> findAll() {
        System.out.println("this is findAll");
        return ResponseEntity.ok(this.service.findAll());
    }

    @ApiOperation(value = "상세 조회")
    @GetMapping(value="/{id}")
    @SimpleRestMethodMapping(SimpleRestMethod.FIND_ONE)
    public final ResponseEntity<T> findById(@PathVariable final I id) {
        logger.debug("try get {} with id [{}]", this.resource, id);
        T object = getObject(id);
        return ResponseEntity.ok(object);
    }

    @ApiOperation(value = "생성")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SimpleRestMethodMapping(SimpleRestMethod.CREATE)
    public final ResponseEntity<T> create(@RequestBody final T createObject) {
        SimpleUtil.mustNotNull(createObject);
        logger.debug("try create {} with [{}]", this.resource.getDescription(), createObject);
        T createdObject = this.service.save(createObject);
        return ResponseEntity.ok(createdObject);
    }

    @ApiOperation(value = "갱신")
    @PostMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SimpleRestMethodMapping(SimpleRestMethod.UPDATE)
    public final ResponseEntity<T> update(@PathVariable final I id, @RequestBody final Map<String, Object> newObjectMap) {
        SimpleUtil.mustNotNull(id);
        SimpleUtil.mustNotNull(newObjectMap);
        SimpleUtil.mustMin(newObjectMap.keySet(), 1);
        logger.debug("try update {} with id [{}] to [{}]", this.resource.getDescription(), id, newObjectMap);
        T updatedEntity = this.service.update(id, newObjectMap);
        logger.debug("updated with [{}]", updatedEntity);
        return ResponseEntity.ok(updatedEntity);
    }

    @ApiOperation(value = "삭제")
    @DeleteMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @SimpleRestMethodMapping(SimpleRestMethod.DELETE)
    public final ResponseEntity<Boolean> delete(@PathVariable final I id) {
        this.service.delete(id);
        return ResponseEntity.ok(true);
    }

    private T getObject(final I id) {
        Optional<T> optionalObject = this.service.findOne(id);
        // return null
        return optionalObject.orElse(null);
    }

    @ExceptionHandler(EntityNotfoundException.class)
    public final ResponseEntity<String> handleUserNotFoundException(EntityNotfoundException e, WebRequest request) {
        return new ResponseEntity<>("not found entity " + this.resource.getDescription(), HttpStatus.NOT_FOUND);
    }
}
