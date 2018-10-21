package star16m.bootsample.resource.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;
import star16m.bootsample.resource.service.ActionExecutor;
import star16m.bootsample.resource.service.action.Action;
import star16m.bootsample.resource.service.action.ActionProvider;
import star16m.bootsample.resource.service.action.ActionRequest;
import star16m.bootsample.resource.service.error.EntityNotfoundException;
import star16m.bootsample.resource.service.error.SimpleException;
import star16m.bootsample.resource.service.error.SimpleRequestException;
import star16m.bootsample.resource.web.controller.annotations.SimpleRestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SimpleRestController(path="/api/rest/action")
public class ActionController {
    private static final Logger log = LoggerFactory.getLogger(ActionController.class);
    private final ActionProvider actionProvider;
    private final ActionExecutor actionExecutor;

    public ActionController(ActionProvider actionProvider, ActionExecutor actionExecutor) {
        this.actionProvider = actionProvider;
        this.actionExecutor = actionExecutor;
    }
    @GetMapping
    public List<Action> getAction() {
        return this.actionProvider.getAllAction();
    }

    @PostMapping
    public List<Map<String, Object>> execute(@Valid @RequestBody ActionRequest actionRequest, Errors errors) {
        if (errors.hasErrors()) {
            String errorMessage = errors.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(", "));
            throw new SimpleRequestException(errorMessage);
        }
        Action action = this.actionProvider.getAction(actionRequest.getActionName()).orElseThrow(() -> new EntityNotfoundException(actionRequest.getActionName()));
        if (!this.actionProvider.isValidParameters(action, actionRequest.getActionParameter())) {
            throw new SimpleRequestException(String.format("Required parameters [%s] is not specified for Action [%s]", action.getRequiredActionParameter(), action.getActionName()));
        }
        log.debug("action [{}], param [{}]", action.getActionName(), actionRequest.getActionParameter());
        return this.actionExecutor.getList(action, actionRequest.getActionParameter());
    }
    @ExceptionHandler(EntityNotfoundException.class)
    public final ResponseEntity<String> handleUserNotFoundException(EntityNotfoundException e, WebRequest request) {
        return new ResponseEntity<>("not found action [" + e.getTargetName() + "]", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SimpleRequestException.class)
    public final ResponseEntity<String> handleUserNotFoundException(SimpleRequestException e, WebRequest request) {
        return new ResponseEntity<>("bad request [" + e.getRequestName() + "]", HttpStatus.BAD_REQUEST);
    }
}
