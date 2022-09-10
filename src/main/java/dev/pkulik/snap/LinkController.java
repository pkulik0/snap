package dev.pkulik.snap;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api")
public class LinkController {
    @Autowired
    private final LinkService linkService;

    @GetMapping
    public List<Link> getAllLinks() {
        return null;
    }

    @GetMapping("/{shortUrl}")
    public void getLink(HttpServletResponse response, @PathVariable String shortUrl) {
        System.out.println("Link: " + shortUrl);
        response.setHeader("Location", "https://google.com");
        response.setStatus(302);

//        response.setStatus(404);
    }
}
