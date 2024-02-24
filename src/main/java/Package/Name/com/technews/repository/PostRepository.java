package Package.Name.com.technews.repository;

import Package.Name.com.technews.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>
{
    List<Post> findAllPostByUserId(Integer id) throws Exception;
}
