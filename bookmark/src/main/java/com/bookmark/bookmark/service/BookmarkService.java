package com.bookmark.bookmark.service;

import com.bookmark.bookmark.dto.BookmarkDto;
import com.bookmark.bookmark.entity.Bookmark;
import com.bookmark.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public List<Bookmark> findBookmarks(String username, String type) {
        return bookmarkRepository.findAllByUsernameAndType(username, type);
    }

    @Transactional
    public void deleteBookmark(Long contentId, String username) {
        bookmarkRepository.deleteByContentIdAndUsername(contentId, username);
    }

    @Transactional
    public Bookmark saveBookmark(Long contentId, String type, BookmarkDto bookmarkDto, String username) {
        Bookmark bookmark = new Bookmark(contentId, type, bookmarkDto, username);
        return bookmarkRepository.save(bookmark);
    }

    public Bookmark checkBookmarkStatus(Long contentId, String username) {
        return bookmarkRepository.findByContentIdAndUsername(contentId, username);
    }

}
