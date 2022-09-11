package dev.pkulik.snap;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api")
public class LinkController {
    @Autowired
    private final LinkService linkService;

    @PostMapping
    public Optional<Link> createLink(@RequestBody String url) {
        return linkService.createLink(url);
    }

    @GetMapping
    public List<Link> getAllLinks() {
        return linkService.getAllLinks();
    }

    @GetMapping("/{shortened}")
    public void getLink(HttpServletResponse response, @PathVariable String shortened) {
        Optional<Link> link = linkService.getByShortened(shortened);

        if (link.isPresent()) {
            response.setHeader("Location", link.get().url);
            response.setStatus(302);
        } else {
            response.setStatus(404);
        }
    }
}
