package com.jj.dheaven.보관용;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class KakaoProfile {

    //다른코드
    /*
    private Integer id;
    private LocalDateTime connectedAt;
    private String email;
    private String nickname;

    public KakaoProfile(String jsonResponseBody){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonResponseBody);

        this.id = element.getAsJsonObject().get("id").getAsInt();

        String connected_at = element.getAsJsonObject().get("connected_at").getAsString();
        connected_at = connected_at.substring(0, connected_at.length() - 1);
        LocalDateTime connectDateTime = LocalDateTime.parse(connected_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.connectedAt = connectDateTime;

        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        this.nickname = properties.getAsJsonObject().get("nickname").getAsString();

        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
        this.email = kakaoAccount.getAsJsonObject().get("email").getAsString();
    }

}
     */

    public Boolean setPrivacyInfo;
    public Long id;
    public String connected_at;
    public Properties properties;
    public Kakao_account kakao_account;
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public class Properties {

        public String nickname;

    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public class Kakao_account {

        public Boolean profile_nickname_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        public class Profile {

            public String nickname;

        }


    }

}








