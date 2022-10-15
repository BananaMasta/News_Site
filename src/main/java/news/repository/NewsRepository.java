package news.repository;

import news.models.News;
import news.models.Tags;
import news.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByTag(Tags tag);

    List<News> findByDateBetween(Date dateBefore, Date dateAfter);

    List<News> getNewsById(long id);

    List<News> findNewsByUser(User user);

    List<News> deleteByUser(User user);

}
