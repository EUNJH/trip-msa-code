package com.bookmark.bookmark.entity;

import com.bookmark.bookmark.dto.BookmarkDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Bookmark {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "BOOKMARK_ID")
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long contentId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String imgUrl;

    @Column
    private String address;

    @Column(nullable = false)
    private String username;

    public Bookmark(Long contentId, String type, BookmarkDto bookmarkDto, String username) {
        this.contentId = contentId;
        this.type = type;
        this.title = bookmarkDto.getTitle();
        this.imgUrl = bookmarkDto.getImgUrl();
        this.address = bookmarkDto.getAddress();
        this.username = username;
    }
}
