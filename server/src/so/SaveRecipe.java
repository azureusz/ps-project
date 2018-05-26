/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.Recipe;
import domain.general.IDomainEntity;
import so.general.AbstractGenericOperation;

/**
 *
 * @author Milos
 */
public class SaveRecipe extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity ide) throws Exception {
    }

    @Override
    protected void execute(IDomainEntity ide) throws Exception {
        Recipe recipe = (Recipe) ide;
        
    }
    
}
