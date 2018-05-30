/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.general;

import db.DatabaseRepository;
import domain.general.IDomainEntity;
import java.util.List;

/**
 *
 * @author Milos
 */
public abstract class AbstractGenericOperation {

    protected DatabaseRepository db;

    public AbstractGenericOperation() {
        db = new DatabaseRepository();
    }

    public void templateExecute(IDomainEntity ide) throws Exception {
        try {
            validate(ide);
            try {
                //validate(ide);
                startTransaction();
                execute(ide);
                commitTransaction();
            } catch (Exception e) {
                rollbackTransaction();//rollback se radi samo ako je transakcija otvorena
                e.printStackTrace();
                throw new Exception("Error: " + e.getMessage());
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public void templateExecute(IDomainEntity ide, List<IDomainEntity> ides) throws Exception {

        try {
            execute(ide, ides);
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }

    }

    protected abstract void validate(IDomainEntity ide) throws Exception;

    private void startTransaction() throws Exception {
        db.startTransaction();
    }

    protected abstract void execute(IDomainEntity ide) throws Exception;

    protected abstract void execute(IDomainEntity ide, List<IDomainEntity> ides) throws Exception;
    
    private void commitTransaction() throws Exception {
        db.commitTransaction();
    }

    private void rollbackTransaction() throws Exception {
        db.rollbackTransaction();
    }
}
