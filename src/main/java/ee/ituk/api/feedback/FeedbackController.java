package ee.ituk.api.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public FeedbackDto insertFeedback(@RequestBody FeedbackDto form) {
        return feedbackService.insert(form);
    }

    @GetMapping
    public List<FeedbackDto> getAll() {
        return feedbackService.getAll();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        feedbackService.delete(id);
    }
}
