/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.util;

/**
 *
 * @author Milos
 */
public interface IOperation {
    public static final int SAVE_RECIPE = 1;
    public static final int SAVE_INGREDIENT = 2;
    public static final int SAVE_RECIPE_CATEGORY = 3;
    public static final int DELETE_RECIPE = 4;
    public static final int DELETE_INGREDIENT = 5;
    public static final int DELETE_RECIPE_CATEGORY = 6;
    public static final int LOAD_ALL_RECIPES = 7;
    public static final int LOAD_ALL_INGREDIENTS = 8;
    public static final int LOAD_ALL_MEASURE_UNITS = 9;
    public static final int LOAD_ALL_RECIPE_CATEGORIES = 10;
    
}
