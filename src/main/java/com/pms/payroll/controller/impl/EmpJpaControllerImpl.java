/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pms.payroll.controller.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.pms.payroll.controller.EmpJpaController;
import com.pms.payroll.controller.impl.exceptions.NonexistentEntityException;
import com.pms.payroll.controller.impl.exceptions.PreexistingEntityException;
import com.pms.payroll.model.PmsEmp;
import com.pms.payroll.model.PmsEmpPK;

/**
 * @author Muzaffar Mohammed, 
 * 		   +91 9951204368
 */
@Repository("empJpaControllerImpl")
public class EmpJpaControllerImpl implements EmpJpaController, Serializable {


    @PersistenceContext(unitName = "PMSPU")
    private EntityManager em;

    public void create(PmsEmp pmsEmp) throws PreexistingEntityException, Exception {
        if (pmsEmp.getPmsEmpPK() == null) {
            pmsEmp.setPmsEmpPK(new PmsEmpPK());
        }
        try {
            em.persist(pmsEmp);
        } catch (Exception ex) {
            if (findPmsEmp(pmsEmp.getPmsEmpPK()) != null) {
                throw new PreexistingEntityException("PmsEmp " + pmsEmp + " already exists.", ex);
            }
            throw ex;
        } 
    }

    public void edit(PmsEmp pmsEmp) throws NonexistentEntityException, Exception {
        try {
            pmsEmp = em.merge(pmsEmp);
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
            	PmsEmpPK pmsEmpPK = pmsEmp.getPmsEmpPK();
                if (findPmsEmp(pmsEmpPK) == null) {
                    throw new NonexistentEntityException("The PmsEmp with pmsEmpPK " + pmsEmpPK + " no longer exists.");
                }
            }
            throw ex;
        } finally {
        }
    }

    public void destroy(PmsEmpPK id) throws NonexistentEntityException {
        try {
        	PmsEmp pmsEmp;
            try {
                pmsEmp = em.getReference(PmsEmp.class, id);
                pmsEmp.getPmsEmpPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The PmsEmp with id " + id + " no longer exists.", enfe);
            }
            em.remove(pmsEmp);
        } catch(Exception e) {
        	System.out.println(e);
        }
    }

    public List<PmsEmp> findPmsEmpEntities() {
        return findPmsEmpEntities(true, -1, -1);
    }

    public List<PmsEmp> findPmsEmpEntities(int maxResults, int firstResult) {
        return findPmsEmpEntities(false, maxResults, firstResult);
    }

    public List<PmsEmp> findPmsEmpEntities(boolean all, int maxResults, int firstResult) {
    	 Query q = null;
    	try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PmsEmp.class));
            q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            
        } catch(Exception e) {
        	System.out.println(e);
        }
        return q.getResultList();
    }

    public PmsEmp findPmsEmp(PmsEmpPK pmsEmpPK) {
        try {
            return em.find(PmsEmp.class, pmsEmpPK);
        } finally {
        	
        }
    }

    public int getPmsEmpCount() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PmsEmp> rt = cq.from(PmsEmp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
        }
    }
    
    public PmsEmp getPmsEmpById(Integer id) {
        try {
            Query q = em.createQuery("select e from PmsEmp e where e.pmsEmpPK.id=:id");
            q.setParameter("id", id);
            return (PmsEmp) q.getSingleResult();
        } catch (Exception e) {
        	System.out.println(e);
            return null;
        }
    }
    
    public void deletePmsEmpById(Integer id) {
    	try {
    		 Query q = em.createQuery("delete from PmsEmp e where e.pmsEmpPK.id=:id");
    	        q.setParameter("id", id);
    	        q.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
    }
    
    public PmsEmp checkUser(String user, String pwd) {
        try {
            Query q = em.createQuery("select e from PmsEmp e where e.user=:user and e.pwd=:pwd");
            q.setParameter("user", user);
            q.setParameter("pwd", pwd);
            return (PmsEmp) q.getSingleResult();
        } catch (Exception e) {
        	System.out.println(e);
            return null;
        }
    }
    
}
