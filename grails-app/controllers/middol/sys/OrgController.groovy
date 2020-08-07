package middol.sys

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class OrgController {

    OrgService orgService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond orgService.list(params), model:[orgCount: orgService.count()]
    }

    def show(Long id) {
        respond orgService.get(id)
    }

    @Transactional
    def save(Org org) {
        if (org == null) {
            render status: NOT_FOUND
            return
        }
        if (org.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond org.errors
            return
        }

        try {
            orgService.save(org)
        } catch (ValidationException e) {
            respond org.errors
            return
        }

        respond org, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Org org) {
        if (org == null) {
            render status: NOT_FOUND
            return
        }
        if (org.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond org.errors
            return
        }

        try {
            orgService.save(org)
        } catch (ValidationException e) {
            respond org.errors
            return
        }

        respond org, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || orgService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
