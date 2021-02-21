package com.cogent.tutorial.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cogent.tutorial.hibernate.entity.Customer;

public class MainApp {

   private static final Logger logger = LogManager.getLogger(MainApp.class);

   public static void main(String[] args) {
      Session session = null;
      Transaction transaction = null;
      try {
    	  
         session = HibernateUtil.getSessionFactory().openSession();
         transaction = session.beginTransaction();
         if(!transaction.isActive())  
         transaction.begin();

         Customer customer = new Customer();
         customer.setName("Joe");
         session.persist(customer);

         transaction.commit();

         logger.info("Customer saved successfully...");

      } catch (Exception e) {
         if (transaction != null) {
            transaction.rollback();
         }
         

         logger.error("Failed to save customer..." + e);
      } finally {
         if (session != null) {
            session.close();
         }
      }

      HibernateUtil.shutdown();
   }
}