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

            entityManager.createQuery("select m from Member m order by m.age desc", Member.class)
                            .setFirstResult(1)
                            .setMaxResults(10)
                            .getResultList();

            entityManager.createQuery("select m from Member m join m.team t", Member.class)
                            .getResultList();

            entityManager.createQuery("select count(m) from Member m, Team t where m.userName = t.name", Member.class)
                            .getResultList();

            entityManager.createQuery("select m, t from Member m left join m.team t on t.name = 'A'", Member.class)
                            .getResultList();

            entityManager.createQuery("select m, t from Member m left join Team t on m.userName = t.name", Member.class)
                            .getResultList();

            entityManager.createQuery("select m from Member m where m.age > (select avg(m2.age) from Member m2)", Member.class)
                            .getResultList();

            entityManager.createQuery("select m from Member m where (select count(o) from Order o where m = o.member) > 0", Member.class)
                            .getResultList();

            entityManager.createQuery("select m from Member m where exists (select t from m.team t where t.name = 'teamA'")
                            .getResultList();

            entityManager.createQuery("select o from Order o where o.orderAmount > ALL (select p.stockAmount from Product p)", Order.class)
                            .getResultList();

            entityManager.createQuery("select m from Member m where m.team = ANY (select t from Team t)", Member.class)
                            .getResultList();

            String jqpl1 = "select " +
                            "case when m.age <= 10 then '학생요금' " +
                            "     when m.age >= 60 then '경로요금' " +
                            "     else '일반요금' " +
                            "end " +
                          "from Member m";

            String jqpl2 = "select coalesce(m.userName, '이름 없는 회원') from Member m";

            String jpql3 = "select NULLIF(m.userName, '관리자') from Member m";

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
