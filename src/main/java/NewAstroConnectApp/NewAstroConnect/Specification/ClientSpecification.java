package NewAstroConnectApp.NewAstroConnect.Specification;

import NewAstroConnectApp.NewAstroConnect.Entity.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    public static Specification<Client> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Client> hasGender(String gender) {
        return (root, query, criteriaBuilder) ->
                gender == null ? null : criteriaBuilder.equal(criteriaBuilder.lower(root.get("gender")), gender.toLowerCase());
    }

    public static Specification<Client> hasAge(String age) {
        return (root, query, criteriaBuilder) ->
                age == null ? null : criteriaBuilder.equal(root.get("age"), age);
    }
}
