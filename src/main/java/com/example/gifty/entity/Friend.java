package com.example.gifty.entity;

import com.example.gifty.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "friends")
public class Friend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private Long kakaoId;

    @Column
    private String kakaoUuid;

    @Column
    private String nickname;

    @Column
    private String profileImage;

    @Builder
    public Friend(User user, Long kakaoId, String kakaoUuid, String nickname, String profileImage) {
        this.user = user;
        this.kakaoId = kakaoId;
        this.kakaoUuid = kakaoUuid;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
