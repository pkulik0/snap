package dev.pkulik.snap.link;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api")
public class LinkController {
    @Autowired
    private final LinkService linkService;

    @PostMapping
    public ResponseEntity<Link> createLink(@RequestBody String url) {
        Optional<Link> optionalLink = linkService.createLink(url);

        if (optionalLink.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(optionalLink.get());
    }

    @GetMapping
    public ResponseEntity<List<Link>> getAllLinks() {
        return ResponseEntity.ok(linkService.getAllLinks());
    }

    @GetMapping("/{shortened}")
    public ResponseEntity<Link> getLink(@PathVariable String shortened) {
        Optional<Link> optionalLink = linkService.getByShortened(shortened);

        if (optionalLink.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(optionalLink.get());
    }

    @DeleteMapping("/{shortened}")
    public ResponseEntity<Void> deleteLink(@PathVariable String shortened) {
        linkService.deleteByShortened(shortened);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{shortened}")
    public ResponseEntity<Link> updateLink(@PathVariable String shortened, @RequestBody String newUrl) {
        Optional<Link> optionalLink = linkService.updateByShortened(shortened, newUrl);

        if (optionalLink.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(optionalLink.get());
    }
}
