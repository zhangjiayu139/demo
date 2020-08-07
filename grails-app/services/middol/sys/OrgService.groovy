package middol.sys

import grails.gorm.services.Service

@Service(Org)
interface OrgService {

    Org get(Serializable id)

    List<Org> list(Map args)

    Long count()

    Org delete(Serializable id)

    Org save(Org org)

}
