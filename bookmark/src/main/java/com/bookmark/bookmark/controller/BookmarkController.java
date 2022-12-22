package com.bookmark.bookmark.controller;

import com.bookmark.bookmark.dto.BookmarkDto;
import com.bookmark.bookmark.entity.Bookmark;
import com.bookmark.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookmarkController {
    private final BookmarkService bookmarkService;

    //@Operation(description = "테마별 인기 여행지 즐겨찾기", method = "POST")
    @PostMapping("/themes/{contentId}/bookmark/{username}")
    public void bookmarkPopular(@PathVariable Long contentId, @PathVariable String username, @RequestBody BookmarkDto bookmarkDto) {

        bookmarkService.saveBookmark(contentId, "popular", bookmarkDto, username);
    }

    //@Operation(description = "테마별 인기 여행지 즐겨찾기 해제", method = "DELETE")
    @DeleteMapping("/themes/{contentId}/bookmark/{username}")
    public void unBookmarkPopular(@PathVariable Long contentId, @PathVariable String username) {
        bookmarkService.deleteBookmark(contentId, username);
    }

    //@Operation(description = "테마별 인기 여행지 즐겨찾기 여부 확인", method = "GET")
    @GetMapping("/themes/{contentId}/bookmark/{username}")
    public Map<String, Boolean> getPopularBookmarkStatus(@PathVariable Long contentId, @PathVariable String username) {
        Map response = new HashMap<String, Boolean>();
        Bookmark bookmark = bookmarkService.checkBookmarkStatus(contentId, username);
        if (bookmark == null) {
            response.put("bookmarkStatus", Boolean.FALSE);
        } else {
            response.put("bookmarkStatus", Boolean.TRUE);
        }
        return response;
    }

    //@Operation(description = "근처 여행지 즐겨찾기", method = "POST")
    @PostMapping("/nearspots/{contentId}/bookmark/{username}")
    public void bookmarkNear(@PathVariable Long contentId, @PathVariable String username, @RequestBody BookmarkDto bookmarkDto) {
        bookmarkService.saveBookmark(contentId, "near", bookmarkDto, username);
    }

    //@Operation(description = "근처 여행지 즐겨찾기 해제", method = "DELETE")
    @DeleteMapping("/nearspots/{contentId}/bookmark/{username}")
    public void unBookmarkNear(@PathVariable Long contentId, @PathVariable String username) {
        bookmarkService.deleteBookmark(contentId, username);
    }

    //@Operation(description = "근처 여행지 즐겨찾기 여부 확인", method = "GET")
    @GetMapping("/nearspots/{contentId}/bookmark/{username}")
    public Map<String, Boolean> getNearBookmarkStatus(@PathVariable Long contentId, @PathVariable String username) {
        Map<String, Boolean> response = new HashMap<>();
        Bookmark bookmark = bookmarkService.checkBookmarkStatus(contentId, username);
        if (bookmark == null) {
            response.put("bookmarkStatus", Boolean.FALSE);
        } else {
            response.put("bookmarkStatus", Boolean.TRUE);
        }
        return response;
    }

    @GetMapping("/bookmarks/{username}")
    public List<Bookmark> sendBookmarks(@RequestParam String type, @PathVariable String username) {
        return bookmarkService.findBookmarks(username, type);
    }
}
