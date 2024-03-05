package Package.Name.com.technews.controller;

import Package.Name.com.technews.model.Comment;
import Package.Name.com.technews.model.Post;
import Package.Name.com.technews.model.User;
import Package.Name.com.technews.repository.CommentRepository;
import Package.Name.com.technews.repository.PostRepository;
import Package.Name.com.technews.repository.UserRepository;
import Package.Name.com.technews.repository.VoteRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomePageController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {

        if (request.getSession(false) != null) {
            return "redirect:/";
        }

        model.addAttribute("user", new User());
        return "login";
    }


    @GetMapping("/users/logout")
    public String logout(HttpServletRequest request)
    {
        if (request.getSession(false) != null)
        {
            request.getSession().invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String homepageSetup(Model model, HttpServletRequest request) {
        User sessionUser = new User();

        if (request.getSession(false) != null) {
            sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
            model.addAttribute("loggedIn", sessionUser.getLoggedIn());
        } else {
            model.addAttribute("loggedIn", false);
        }


        List<Post> postList = postRepository.findAll();
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
            User user = userRepository.getById(p.getUserId());
            p.setUserName(user.getUsername());
        }

        model.addAttribute("postList", postList);
        model.addAttribute("loggedIn", sessionUser.getLoggedIn());

        // "point" and "points" attributes refer to upvotes.
        model.addAttribute("point", "point");
        model.addAttribute("points", "points");

        return "homepage";
    }

    @GetMapping("/dashboard")
    public String dashboardPageSetup(Model model, HttpServletRequest request) throws Exception {
        if (request.getSession(false) != null) {
            setupDashboardPage(model, request);
            return "dashboard";
        } else {
            model.addAttribute("user", new User());
            return "login";
        }
    }

    @GetMapping("/dashboardEmptyTitleAndLink")
    public String dashboardEmptyTitleAndLinkHandler(Model model, HttpServletRequest request) throws Exception {
        setupDashboardPage(model, request);
        model.addAttribute("notice", "To create a post the Title and Link must be populated!");
        return "dashboard";
    }
    @GetMapping("/singlePostEmptyComment/{id}")
    public String singlePostEmptyCommentHandler(@PathVariable("id")Integer id, Model model,HttpServletRequest request)
    {
        setupSinglePostPage(id, model, request);
        model.addAttribute("notice", "To add a comment you must add text in the text area");
        return "single-post";
    }

    @GetMapping("/post/{id}")
    public String singlePostPageSetup(@PathVariable("id") int id, Model model, HttpServletRequest request)
    {
        setupSinglePostPage(id, model, request);
        return "single-post";
    }

    @GetMapping("/editPostEmptyComment/{id}")
    public String editPostEmptyCommentHandler(@PathVariable("id") int id, Model model, HttpServletRequest request)
    {
        if (request.getSession(false) != null)
        {
            setupEditPostPage(id, model, request);
            model.addAttribute("notice", "To add a comment you must add text to the text area");
            return "edit-post";
        }
        else
        {
            model.addAttribute("user", new User());
            return "login";
        }
    }

    @GetMapping("/dashboard/edit/{id}")
    public String editPostPageSetup(@PathVariable("id") int id, Model model, HttpServletRequest request)
    {
        if (request.getSession(false) != null)
        {
            setupEditPostPage(id, model, request);
            return "edit-post";
        }
        else
        {
            model.addAttribute("user", new User());
            return "login";
        }
    }

    public Model setupDashboardPage(Model model, HttpServletRequest request) throws Exception {
        User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
        Integer userId = sessionUser.getId();
        List<Post> postList = postRepository.findAllPostByUserId(userId);
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
            User user = userRepository.getById(p.getUserId());
            p.setUserName(user.getUsername());
        }
        model.addAttribute("user", sessionUser);
        model.addAttribute("postList", postList);
        model.addAttribute("loggedIn", sessionUser.getLoggedIn());
        model.addAttribute("post", new Post());
        return model;
    }

    public Model setupSinglePostPage(int id, Model model, HttpServletRequest request)
    {
        if (request.getSession(false) != null)
        {
            User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
            model.addAttribute("sessionUser", sessionUser);
            model.addAttribute("loggedIn", sessionUser.getLoggedIn());
        }

        Post post = postRepository.getById(id);
        post.setVoteCount(voteRepository.countVotesByPostId(post.getId()));

        User postUser = userRepository.getById(post.getUserId());
        post.setUserName(postUser.getUsername());

        List<Comment> commentList = commentRepository.findAllCommentsByPostId(post.getId());

        model.addAttribute("commentList", commentList);
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());

        return model;
    }

    public Model setupEditPostPage(int id, Model model, HttpServletRequest request)
    {
        if (request.getSession(false) != null)
        {
            User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");

            Post returnPost = postRepository.getById(id);
            User tempUser = userRepository.getById(returnPost.getUserId());
            returnPost.setUserName(tempUser.getUsername());
            returnPost.setVoteCount(voteRepository.countVotesByPostId(returnPost.getId()));

            List<Comment> commentList = commentRepository.findAllCommentsByPostId(returnPost.getId());

            model.addAttribute("post", returnPost);
            model.addAttribute("loggedIn", sessionUser.getLoggedIn());
            model.addAttribute("commentList", commentList);
            model.addAttribute("comment", new Comment());
        }

        return model;
    }

}
