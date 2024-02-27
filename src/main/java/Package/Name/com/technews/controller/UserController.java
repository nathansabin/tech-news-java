package Package.Name.com.technews.controller;

import Package.Name.com.technews.repository.UserRepository;
import Package.Name.com.technews.repository.VoteRepository;
import Package.Name.com.technews.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository repository;
    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/api/users")
    public List<User> getALLUsers()
    {
        List<User> userList = repository.findAll();
        for (User u: userList)
        {
            List<Post> postList = u.getPosts();
            for(Post p : postList)
            {
                p.setVoteCount(voteRepository.countVotesByPostId(p.getId() ));
            }
        }
        return userList;
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable("id") Integer id)
    {
        User returnUser = repository.getById(id);
        List<Post> postList = returnUser.getPosts();
        for (Post p: postList)
        {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
        }
        return returnUser;
    }

    @PostMapping("/api/users")
    public User addUser(@RequestBody User user)
    {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        repository.save(user);
        return user;
    }

    @PutMapping("/api/users/{id}")
    public User updateUser(@PathVariable("id") int id, @RequestBody User user)
    {
        User tempUser = repository.getById(id);
        if (!tempUser.equals(null)) {
            user.setId(tempUser.getId());
            repository.save(user);
        }
        return user;
    }

    @DeleteMapping("/api/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") int id) {
        repository.deleteById(id);
    }
}
