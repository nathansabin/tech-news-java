package Package.Name.com.technews.controller;

import Package.Name.com.technews.model.*;
import Package.Name.com.technews.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentRepository repository;

    @GetMapping("/api/comments")
    public List<Comment> getAll()
    {
        List<Comment> tempComments = repository.findAll();
        return tempComments;
    }

    @GetMapping("/api/comments/{id}")
    public Comment getComment(@PathVariable("id") int id)
    {
        return repository.getById(id);
    }

    @PostMapping("/api/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment)
    {
        return repository.save(comment);
    }

    @PutMapping("/api/updateComment")
    public Comment updateComment(@RequestBody Comment comment)
    {
        return repository.save(comment);
    }

    @DeleteMapping("/api/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("id") int id)
    {
        repository.deleteById(id);
    }
}
