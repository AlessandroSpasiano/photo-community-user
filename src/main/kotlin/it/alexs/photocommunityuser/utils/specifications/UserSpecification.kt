package it.alexs.photocommunityuser.utils.specifications

import it.alexs.photocommunityuser.entities.User
import it.alexs.photocommunityuser.utils.criteria.SearchCriteria
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class UserSpecification(
    private val searchCriteria: SearchCriteria
): Specification<User> {

    override fun toPredicate(root: Root<User>, query: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? {
        return when(searchCriteria.operation) {
            "<>" -> criteriaBuilder.notEqual(
                root.get<String>(searchCriteria.key), searchCriteria.value
            )
            "<" -> criteriaBuilder.lessThan(root.get(searchCriteria.key), searchCriteria.value)
            "<=" -> criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.key), searchCriteria.value)
            ">" -> criteriaBuilder.greaterThan(root.get(searchCriteria.key), searchCriteria.value)
            ">=" -> criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.key), searchCriteria.value)
            "=IN" -> root.get<String>(searchCriteria.key).`in`(searchCriteria.value.split(","))
            else -> criteriaBuilder.equal(root.get<String>(searchCriteria.key), searchCriteria.value)
        }
    }
}