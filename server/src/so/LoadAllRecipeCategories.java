/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.general.IDomainEntity;
import java.util.List;
import so.general.AbstractGenericOperation;

/**
 *
 * @author Milos
 */
public class LoadAllRecipeCategories extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity ide) throws Exception {
    }

    @Override
    protected void execute(IDomainEntity ide) throws Exception {
    }

    @Override
    protected void execute(IDomainEntity ide, List<IDomainEntity> ides) throws Exception {
        ides = db.findAll(ide);
    }
    
}
