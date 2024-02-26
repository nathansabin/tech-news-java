package Package.Name.com.technews.controller;

import Package.Name.com.technews.model.*;
import Package.Name.com.technews.model.Post;
import Package.Name.com.technews.model.Vote;
import Package.Name.com.technews.repository.PostRepository;
import Package.Name.com.technews.repository.UserRepository;
import Package.Name.com.technews.repository.VoteRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    PostRepository repository;
    VoteRepository voteRepository;
    UserRepository userRepository;

    @GetMapping("/api/posts")
    public List<Post> getAllPost()
    {
        List<Post> postList = repository.findAll();
        for (Post p : postList)
        {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
        }
        return postList;
    }

    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Integer id)
    {
        Post returnPost = repository.getById(id);
        returnPost.setVoteCount(voteRepository.countVotesByPostId(returnPost.getId()));
        return returnPost;
    }

    @PostMapping("/api/post")
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody Post post)
    {
        repository.save(post);
        return post;
    }

    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody Post post)
    {
        Post tempPost = repository.getById(id);
        tempPost.setTitle(post.getTitle());
        return repository.save(tempPost);
    }

    @PutMapping("/api/posts/upvote")
    public String addVote(@RequestBody Vote vote, HttpServletRequest request)
    {
        String returnValue = "";
        if(request.getSession(false) != null)
        {
            Post returnPost = null;

            User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
            vote.setUserId(sessionUser.getId());
            voteRepository.save(vote);

            returnPost = repository.getById(vote.getPostId());
            returnPost.setVoteCount(voteRepository.countVotesByPostId(vote.getPostId()));

            returnValue = "";
        }
        else
        {
            returnValue = "login";
        }

        return returnValue;
    }

    @DeleteMapping("/api/posts/{id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable int id)
    {
        repository.deleteById(id);
    }
}
