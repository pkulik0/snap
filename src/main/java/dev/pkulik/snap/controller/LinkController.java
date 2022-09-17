package dev.pkulik.snap.controller;

import dev.pkulik.snap.service.LinkService;
import dev.pkulik.snap.model.Link;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api")
public class LinkController {
    private final LinkService linkService;

    @PostMapping("/links")
    public ResponseEntity<Link> createLink(@RequestBody Link url) {
        Optional<Link> optionalLink = linkService.create(url);

        if (optionalLink.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(optionalLink.get());
    }

    @GetMapping("/links")
    public ResponseEntity<Collection<Link>> getUsersLinks() {
        return ResponseEntity.ok(linkService.getOwned());
    }

    @GetMapping("/links/{shortened}")
    public ResponseEntity<Link> getLink(@PathVariable String shortened) {
        Optional<Link> optionalLink = linkService.getByShortened(shortened);

        if (optionalLink.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(optionalLink.get());
    }

    @DeleteMapping("/links/{shortened}")
    public ResponseEntity<Void> deleteLink(@PathVariable String shortened) {
        linkService.deleteByShortened(shortened);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/links/{shortened}")
    public ResponseEntity<Link> updateLink(@PathVariable String shortened, @RequestBody Link link) {
        if(!shortened.equals(link.getShortened()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Link> optionalLink = linkService.updateByShortened(shortened, link);

        if (optionalLink.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(optionalLink.get());
    }

    @GetMapping("/{shortened}")
    public void visitLink(HttpServletResponse response, @PathVariable String shortened) {
        Optional<Link> link = linkService.getByShortened(shortened);

        if(link.isEmpty()) {
            response.setStatus(404);
        } else {
            response.setHeader("Location", link.get().getUrl());
            response.setStatus(302);
        }
    }
}
