package com.jj.dheaven.repository.queryDsl;

import com.jj.dheaven.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
@Transactional
public class QuerydslApplicationTests {

    @Autowired
    EntityManager em;

   // JPAQueryFactory jpaQueryFactory;


 /*   @BeforeEach
    void init() {
        jpaQueryFactory = new JPAQueryFactory(em);

        Champion aatrox = createChampion("Aatrox", 1000, "아트록스", LocalDateTime.now());
        Champion teemo = createChampion("Teemo", 2000, "티모", LocalDateTime.now());
        Champion ahri = createChampion("Ahri", 3000, "아리", LocalDateTime.now());
        User u1 =
        em.persist(aatrox);
        em.persist(teemo);
        em.persist(ahri);
    }*/

/*
    @DisplayName("QueryDsl test >>insert 잘 되는가")
    @Test
    void contextLoads() {
       // User user = new User();
       // em.persist(user);

        JPAQueryFactory query = new JPAQueryFactory(em);
       // QUser quser = QUser.user;

        //User result = query.selectFrom(quser).fetchOne();

       // Assert.assertThat()



        System.out.println("quest dsl 테스트");


    }
*/




}
