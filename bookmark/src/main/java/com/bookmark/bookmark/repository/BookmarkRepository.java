package com.bookmark.bookmark.repository;

import com.bookmark.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findAllByUsernameAndType(String username, String type);

    void deleteByContentIdAndUsername(Long contentId, String username);

    Bookmark findByContentIdAndUsername(Long contentId, String username);
}
