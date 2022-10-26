package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Team teamA = new Team();
            teamA.setName("teamA");
            entityManager.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            entityManager.persist(teamB);

            Member member1 = new Member();
            member1.setUserName("member1");
            member1.setTeam(teamA);
            entityManager.persist(member1);

            Member member2 = new Member();
            member2.setUserName("member2");
            member2.setTeam(teamA);
            entityManager.persist(member2);

            Member member3 = new Member();
            member3.setUserName("member3");
            member3.setTeam(teamB);
            entityManager.persist(member3);

            entityManager.flush();
            entityManager.clear();

            String query = "select distinct t from Team t join fetch t.members";

            List<Team> resultList = entityManager.createQuery(query, Team.class)
                    .getResultList();

            System.out.println("==============================");

            for (Team team : resultList) {
                System.out.println("team.getMembers().size() = " + team.getMembers().size());
                System.out.println("team.getName() = " + team.getName());

                for (Member member : team.getMembers()) {
                    System.out.println("member.getUserName() = " + member.getUserName());
                }
            }

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
