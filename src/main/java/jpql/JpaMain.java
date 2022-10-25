package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Member member = new Member();
            member.setUserName("member1");
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            entityManager.createQuery("select t from Member m join m.team t", Team.class)
                    .getResultList();

            entityManager.createQuery("select new jpql.MemberDto(m.userName, m.age) from Member m", MemberDto.class)
                    .getResultList();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
