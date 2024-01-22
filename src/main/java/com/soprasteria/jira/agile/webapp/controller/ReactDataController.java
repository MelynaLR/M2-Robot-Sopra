import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/static/api/data")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET})

public class ReactDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactDataController.class);

    @GetMapping(produces = "application/json")
    public ResponseEntity<String> getData() {
        String message = { "message": "Hello from Spring Boot!"
                            }

        LOGGER.info("Message sent to the client: {}", message);
        return ResponseEntity.ok(message);
    }
}
