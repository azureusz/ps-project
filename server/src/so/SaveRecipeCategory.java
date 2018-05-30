/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import domain.RecipeCategory;
import domain.general.IDomainEntity;
import java.util.List;
import so.general.AbstractGenericOperation;

/**
 *
 * @author Milos
 */
public class SaveRecipeCategory extends AbstractGenericOperation{

    @Override
    protected void validate(IDomainEntity ide) throws Exception {
        RecipeCategory rc = (RecipeCategory) ide;
        if(rc.getName().isEmpty()) throw new Exception("Category name can't be empty.");
    }

    @Override
    protected void execute(IDomainEntity ide) throws Exception {
        ide = db.save(ide);
    }

    @Override
    protected void execute(IDomainEntity ide, List<IDomainEntity> ides) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
