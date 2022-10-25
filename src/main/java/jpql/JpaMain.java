package jpql;

import javax.persistence.*;

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

            TypedQuery<Member> query1 = entityManager.createQuery("select m from Member as m", Member.class);
            TypedQuery<String> query2 = entityManager.createQuery("select m.userName from Member as m", String.class);
            Query query3 = entityManager.createQuery("select m.userName, m.age from Member as m");

            Member singleResult1 = query1.getSingleResult();
            String singleResult2 = query2.getSingleResult();
            Object singleResult3 = query3.getSingleResult();

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
